## Report for the micro-service order

#### Package entities
+ La classe Order
```java
@Entity
@Table(name="orders")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;
    private OrderStatus status;
    private Long customerId;
    @Transient
    private Customer customer;
    @OneToMany(mappedBy = "order")
    private List<ProductItem> productItems;
    public double getTotal(){
        double somme=0;
        for(ProductItem pi:productItems){
            somme+=pi.getAmount();
        }
        return somme;
    }
}
```

+ L'interface OrderProjection
```java
@Projection(name="fullOrder",types = Order.class)
public interface OrderProjection {
    Long getId();
    Date getCreatedAt();
    Long getCustomerId();
    OrderStatus getStatus();
}
```

+ La classe ProductItem
```java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    @Transient
    private Product product;
    private double price;
    private int quantity;
    private double discount;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Order order;
    public double getAmount(){
        return price*quantity*(1-discount);
    }
}
```

#### Package enums
+ L'enumeration OrderStatus
```java
public enum OrderStatus {
    CREATED, PENDING, DELIVERED, CANCELED
}
```

#### Package models
+ La classe Customer
```java
@Data
public class Customer {
    private Long id;
    private String name;
    private String email;
}
```

+ La classe Product
```java
@Data
public class Product {
    private Long id;
    private String name;
    private double price;
    private int quantity;
}
```

#### Package repos
+ L'interface OrderRepository
```java
@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Long> {
    @RestResource(path = "/byCustomerId")
    List<Order> findByCustomerId(@Param("customerId") Long customerId);
}
```

+ L'interface ProductItemRepository
```java
@RepositoryRestResource
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
}
```

#### Package services
+ L'interface CustomerRestClientService
```java
@FeignClient(name = "customer-service")
public interface CustomerRestClientService {
    @GetMapping("/customers/{id}?projection=fullCustomer")
    public Customer customerById(@PathVariable Long id);
    @GetMapping("/customers?projection=fullCustomer")
    public PagedModel<Customer> allCustomers();
}
```

+ L'interface InventoryRestClientService
```java
@FeignClient(name = "inventory-service")
public interface InventoryRestClientService {
    @GetMapping("/products/{id}?projection=fullProduct")
    public Product productById(@PathVariable Long id);
    @GetMapping("/products?projection=fullProduct")
    public PagedModel<Product> allProducts();
}
```

#### Package web
+ La classe OderRestController
```java
@RestController
public class OderRestController {
    private OrderRepository orderRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClientService customerRestClientService;
    private InventoryRestClientService inventoryRestClientService;
    public OderRestController(OrderRepository orderRepository, ProductItemRepository productItemRepository, CustomerRestClientService customerRestClientService, InventoryRestClientService inventoryRestClientService) {
        this.orderRepository = orderRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClientService = customerRestClientService;
        this.inventoryRestClientService = inventoryRestClientService;
    }
    @GetMapping("/fullOrder/{id}")
    public Order getOrder(@PathVariable Long id){
        Order order=orderRepository.findById(id).get();
        Customer customer=customerRestClientService.customerById(order.getCustomerId());
        order.setCustomer(customer);
        order.getProductItems().forEach(pi->{
            Product product=inventoryRestClientService.productById(pi.getProductId());
            pi.setProduct(product);
        });
        return order;
    }
}
```

#### Main
+ La classe OrderServiceApplication
```java
@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(
			OrderRepository orderRepository,
			ProductItemRepository productItemRepository,
			CustomerRestClientService customerRestClientService,
			InventoryRestClientService inventoryRestClientService){
		return args -> {
			List<Customer> customers=customerRestClientService.allCustomers().getContent().stream().toList();
			List<Product> products=inventoryRestClientService.allProducts().getContent().stream().toList();
			Long customerId=1L;
			Random random=new Random();
			Customer customer=customerRestClientService.customerById(customerId);
			for (int i = 0; i <20 ; i++) {
				Order order=Order.builder()
						.customerId(customers.get(random.nextInt(customers.size())).getId())
						.status(Math.random()>0.5?OrderStatus.PENDING:OrderStatus.CREATED)
						.createdAt(new Date())
						.build();
				Order savedOrder = orderRepository.save(order);
				for (int j = 0; j < products.size() ; j++) {
					if(Math.random()>0.70){
						ProductItem productItem=ProductItem.builder()
								.order(savedOrder)
								.productId(products.get(j).getId())
								.price(products.get(j).getPrice())
								.quantity(1+random.nextInt(10))
								.discount(Math.random())
								.build();
						productItemRepository.save(productItem);
					}
				}
			}
		};
	}
}
```

#### Fichier application.properties
```java
server.port=8083
spring.application.name=order-service
spring.config.import=optional:configserver:http://localhost:8888
logging.level.org.sid.orderservice.services.CustomerRestClientService=debug
logging.level.org.sid.orderservice.services.InventoryRestClientService=debug
feign.client.config.default.loggerLevel=full
```

#### Tests
+ Le service order est lancé avec succès sur consul
  
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/23bcbe1d-d999-4525-bc6b-f4eb5a7120b7)

+ Pour voir la liste des ordres, on tape `http://localhost:8083/orders`

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/1718e3c7-cc26-4a7d-b307-f4cde1f46f8e)

+ Pour voir le 1er order, on tape `http://localhost:8083/orders/1`

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/be63d158-15b1-4cc1-9d59-d36315eb6d60)

+ Pour voir l'ensemble des produits du 1er ordre à partir de la gateway, on tape `http://localhost:9999/order-service/fullOrder/1`

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/f9aac293-85e6-4b7e-b2e0-805ea84d2657)





