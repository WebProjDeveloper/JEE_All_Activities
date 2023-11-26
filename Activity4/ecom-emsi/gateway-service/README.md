## Report for the micro-service gateway

#### Main  
+ La classe GatewayServiceApplication
```java
@SpringBootApplication
public class GatewayServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}
	@Bean
	DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient rdc,
                                                            DiscoveryLocatorProperties dlp){
		return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
	}
}
```

#### Fichier application.properties
```java
server.port=9999
spring.application.name=gateway-service
spring.config.import=optional:configserver:http://localhost:8888
```

#### Fichier application.yml
```java
spring:
  cloud:
    gateway:
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins: "http://localhost:4200"
            allowedHeaders: "*"
            allowedMethods:
              - GET
              - POST
              - PUT
              - DELETE
```

#### Tests
+ Le service gateway est lancé avec succès sur consul
  
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/a2a0b7dd-1337-4e6d-8ffe-91ce5b884e8b)

+ Pour voir la liste des customers à partir de la gateway, on tape `http://localhost:9999/customer-service/customers`

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/21e38ba5-b79a-4dbb-8b26-fee83c3aeb48)

+ Pour voir la liste des produits à partir de la gateway, on tape `http://localhost:9999/inventory-service/products`

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/261753f6-a4a7-4fe6-ac3d-16b3fbedf74c)



