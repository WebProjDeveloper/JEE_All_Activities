# Activity 3 : Mise en oeuvre d'une architecture Micro-Services

## Objectif de l'activité
Créer une application basée sur une architecture micro-service qui permet de gérer les factures contenant des produits et appartenant à un client.

### Customer service 
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

### Inventory service 
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

### Gateway
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
