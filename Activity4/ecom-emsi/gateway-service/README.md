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
