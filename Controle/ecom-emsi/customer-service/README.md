## Report for the micro-service customer

#### Package entities

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

+ L'interface CustomerProjection

```java
@Projection(name = "fullCustomer",types = Customer.class)
public interface CustomerProjection {
    public Long getId();
    public String getName();
    public String getEmail();
}
```

#### Package repo

+ L'interface CustomerRepository

```java
@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
```

#### Package web
+ La classe CustomerProjection
```java
@RestController
@RefreshScope
public class CustomerConfigTestController {
    @Value("${global.params.p1}")
    private String p1;
    @Value("${global.params.p2}")
    private String p2;
    @Value("${customer.params.x}")
    private String x;
    @Value("${customer.params.y}")
    private String y;
    @GetMapping("/params")
    public Map<String, String> params(){
        return Map.of("p1",p1,"p2",p2,"x",x,"y",y);
    }
}
```


#### Main
+ La classe CustomerServiceApplication
```java
@SpringBootApplication
public class CustomerServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(CustomerRepository customerRepository){
		return args -> {
		   	customerRepository.saveAll(List.of(
					Customer.builder().name("Housna").email("Housna@gmail.com").build(),
					Customer.builder().name("Ouassima").email("Ouassima@gmail.com").build(),
					Customer.builder().name("Hanae").email("Hanae@gmail.com").build()
			));
			   customerRepository.findAll().forEach(System.out::println);
		};
	}

}
```


#### Fichier application.properties

```java
server.port=8081
spring.application.name=customer-service
spring.config.import=optional:configserver:http://localhost:8888
```

#### Tests 
+ Le service customer est lancé avec succès sur consul

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/4d232ae6-5aac-430c-b4a5-5a19bc13cf1d)

- Par défaut
  
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/7405b7cb-db68-4acd-b259-0253a767d930)

- En dev

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/bbbb516d-8406-44eb-bcce-726f4b451f0e)

- En prod

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/52ea5782-029e-44f8-a1ac-6c6d8d1ff5b0)

- Pour voir la liste des customers, on tape `http://localhost:8081/customers`

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/b73a1866-75e9-4770-b01b-96d3c3a29664)

- Pour voir le 1er customer, on tape `http://localhost:8081/customers/1`

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/0ab680c6-5968-48da-8dd8-d07e79f10539)

- Sur la console

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/0c3d3a46-f958-45df-855f-607d9a7b19bf)

- H2 console (La table customer)

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/ff1f8bf9-428f-440b-924a-6fd43cc279a4)





