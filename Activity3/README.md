# Activity 3 : Mise en oeuvre d'une architecture Micro-Services

## Objectif de l'activité
Créer une application basée sur une architecture micro-service qui permet de gérer les factures contenant des produits et appartenant à un client.

## Structure du projet


![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/d26636d1-ee5b-44ca-8017-fac4d9f207b0)


### *** Customer service ***
####  Les entités JPA 
  + La classe Customer
```java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
}
```
 + L'interface customer projection
```java
@Projection(name = "fullCustomer", types = Customer.class)
public interface CustomerProjection {
    public Long getId();
    public String getName();
}
```

### Les repositories
- L'interface BankAccountRepository 
```java 
@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
```

### La classe Main
- La classe CustomerServiceApplication
```java
@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner(CustomerRepository customerRepository,
                                               RepositoryRestConfiguration restConfiguration){
        return args -> {
            restConfiguration.exposeIdsFor(Customer.class);
            customerRepository.saveAll(
                    List.of(
                            Customer.builder().name("Housna").email("Housna@gmail.com").build(),
                            Customer.builder().name("Hayat").email("Hayat@gmail.com").build(),
                            Customer.builder().name("Ouassima").email("Ouassima@gmail.com").build()
                    )
            );
            customerRepository.findAll().forEach(c->{
                System.out.println(c);
            });
        };
    }

}
```

### Le fichier de configuration
- Le fichier application.properties
```java
spring.application.name=customer-service
server.port=9081
spring.datasource.url=jdbc:h2:mem:customer-db
spring.h2.console.enabled=true
spring.cloud.discovery.enabled=true
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
```

### *** Inventory service ***
####  Les entités JPA 
  + La classe Product
```java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int quantity;
}
```

### Les repositories
- L'interface ProductRepository 
```java 
@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {
}
```

### La classe Main
- La classe InventoryServiceApplication
```java
@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(ProductRepository productRepository, RepositoryRestConfiguration restConfiguration){
        return args -> {
            restConfiguration.exposeIdsFor(Product.class);
            productRepository.saveAll(
                    List.of(
                            Product.builder().name("PC Dell").quantity(20).price(5000).build(),
                            Product.builder().name("Smart TV").quantity(10).price(100000).build(),
                            Product.builder().name("Iphone 15").quantity(5).price(30000).build()
                            )
            );
        };
    }
}
```

### Le fichier de configuration
- Le fichier application.properties
```java
spring.application.name=inventory-service
server.port=9082
spring.datasource.url=jdbc:h2:mem:products-db
spring.cloud.discovery.enabled=true
#management.endpoints.web.exposure.include=*
```

### *** Gateway ***
### La classe Main
- La classe GatewayApplication
```java
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
    //@Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
                .route((r)->r.path("/customers/**").uri("lb://CUSTOMER-SERVICE"))
                .route((r)->r.path("/products/**").uri("lb://INVENTORY-SERVICE"))
                .build();
    }
    @Bean
    public DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp)
    {
        return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
    }
}
```

### Le fichier de configuration
- Le fichier application.yml
```java
server:
  port: 9999
spring:
  application:
    name: gateway-service
  cloud:
    discovery:
      enabled: true
#    gateway:
#      routes:
#        - id: r1
#          uri: http://localhost:8081
#          predicates:
#            - Path= /customers/**
#        - id: r2
#          uri: http://localhost:8082
#          predicates:
#            - Path= /products/**
```
### *** Discovery service ***
### La classe Main
- La classe DiscoveryServiceApplication
```java
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServiceApplication.class, args);
    }
}
```

### Le fichier de configuration
- Le fichier application.properties
```java
server.port=8761
eureka.client.fetch-registry=false
eureka.client.register-with-eureka=false
```

### *** Billing service *** 
####  Les entités JPA 
  + La classe Bill
```java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Bill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date billDate;
    private Long customerId;
    @OneToMany(mappedBy = "bill")
    private List<ProductItem> productItems;
    @Transient
    private Customer customer;
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
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Bill bill;
    private double quantity;
    private double price;
    private double discount;
    @Transient
    private Product product;
}
```

### Les repositories
- L'interface BillRepository 
```java 
@RepositoryRestResource
public interface BillRepository extends JpaRepository<Bill,Long> {
}
```

- L'interface ProductItemRepository 
```java 
@RepositoryRestResource
public interface ProductItemRepository extends JpaRepository<ProductItem,Long> {
    public Collection<ProductItem> findAllById(Long id);
}
```
### model
- La classe Customer
```java
@Data
public class Customer {
    private Long id;
    private String name;
    private String email;
}
```

- La classe Product
```java
@Data
public class Product {
    private Long id;
    private String name;
    private double price;
    private double quantity;

}
```

### Les services
- L'interface CustomerRestClient 
```java 
@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerRestClient {
    @GetMapping(path="/customers/{id}")
    Customer findCustomerById(@PathVariable Long id);
}
```

- L'interface ProductRestClient 
```java 
@FeignClient(name = "INVENTORY-SERVICE")
public interface ProductRestClient {
    @GetMapping(path="/products/{id}")
    Product findProductById(@PathVariable Long id);
    @GetMapping(path="/products")
    PagedModel<Product> allProducts();
}
```

### La couche web
- La classe BillRestController
```java 
@RestController
public class BillRestController {
    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductRestClient productRestClient;

    public BillRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductRestClient productRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    }

    @GetMapping(path = "/fullBill/{id}")
    public Bill bill(@PathVariable Long id){
        Bill bill = billRepository.findById(id).get();
        bill.setCustomer(customerRestClient.findCustomerById(bill.getCustomerId()));
        bill.getProductItems().forEach(pi->{
            pi.setProduct(productRestClient.findProductById(pi.getProductId()));
        });
        return bill;
    }
}
```

### La classe Main
- La classe DiscoveryServiceApplication
```java
@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(BillRepository billRepository,
                            ProductItemRepository productItemRepository,
                            CustomerRestClient customerRestClient,
                            ProductRestClient productRestClient){

        return args -> {
            Collection<Product> products = productRestClient.allProducts().getContent();
            Long customerId = 1L;
            Customer customer = customerRestClient.findCustomerById(customerId);
            if(customer==null) throw new RuntimeException("Customer not found");
            Bill bill = new Bill();
            bill.setBillDate(new Date());
            bill.setCustomerId(customerId);
            Bill savedBill = billRepository.save(bill);
            products.forEach(product -> {
                ProductItem productItem = new ProductItem();
                productItem.setBill(savedBill);
                productItem.setProductId(product.getId());
                productItem.setQuantity(1+new Random().nextInt(10));
                productItem.setPrice(product.getPrice());
                productItem.setDiscount(Math.random());
                productItemRepository.save(productItem);
            });
        };
    }
}
```

### Le fichier de configuration
- Le fichier application.properties
```java
server.port=9083
spring.application.name=billing-service
spring.datasource.url=jdbc:h2:mem:bill-db
spring.h2.console.enabled=true
```

### Les tests

- Test du micro service customer `http://localhost:9081/customers`
  
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/a855fc64-b908-4933-85ff-e34a9e5b0f66)

- Test du `http://localhost:9081/customers/1`

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/76b3acdc-42d7-4632-8872-cac20ed5cf2e)

- Test du `http://localhost:9081/actuator/health` 

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/372cd8b2-8976-4bc4-b068-058f56633716)

- Test du micro service inventory `http://localhost:9082/products` 

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/a444cfa1-fcd5-45f5-ac0e-3619958cca20)

- Les instances enregistrées avec Eureka 

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/5e26a23f-a31d-498c-a0d4-7e5ff83ea3b6)

- Test du micro service billing `http://localhost:9083/bills` 

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/2bf05284-909f-411e-bcc9-30c90e58c829)

- Test du `http://localhost:9083/fullBill/1` 

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/dd97956c-8262-45a5-8668-38fad14e5bbf)

### H2 console

- La table customer

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/6de7e36f-1581-4bcd-8b72-70ed476a1ca0)







