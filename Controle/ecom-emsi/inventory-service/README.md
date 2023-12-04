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
+ La classe InventoryServiceApplication
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

#### Tests
+ Le service inventory est lancé avec succès sur consul
  
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/0cdeb59c-4db7-4398-8b9d-6cce350dc4bc)

+ Pour voir la liste des produits, on tape `http://localhost:8082/products`

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/7895c708-2ee3-4f62-8cea-0c9d4cab35ab)

+ Pour voir le 2ème produit, on tape `http://localhost:8081/products/2`

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/3ff0b464-e2ec-4cd4-a6f0-b6d594fa4ece)

+ H2 console (La table product)

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/fa39cb12-9270-4821-9711-8f848a268d53)





