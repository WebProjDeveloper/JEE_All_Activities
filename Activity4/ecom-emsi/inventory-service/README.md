## Report for the micro-service inventory

#### Package entities
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

+ L'interface ProductProjection
```java
@Projection(name = "fullProduct",types = Product.class)
public interface ProductProjection {
    public Long getId();
    public String getName();
    public double getPrice();
    public int getQuantity();
}
```

#### Package repo
+ L'interface ProductRepository
```java
@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {
}
```

#### Main
+ La classe CustomerServiceApplication
```java
@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(ProductRepository productRepository){
		return args -> {
			Random random=new Random();
			for (int i = 1; i <10 ; i++) {
				productRepository.saveAll(List.of(
						Product.builder()
								.name("Computer Dell "+i)
								.price(1200+Math.random()*10000)
								.quantity(1+random.nextInt(200)).build()
		        ));
			}
		};
	}
}
```

#### Fichier application.properties
```java
server.port=8082
spring.application.name=inventory-service
spring.config.import=optional:configserver:http://localhost:8888
```

