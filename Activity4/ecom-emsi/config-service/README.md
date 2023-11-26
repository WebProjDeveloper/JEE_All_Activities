## Report for the micro-service config

- Pour lancer consul sur cmd on tape : `consul agent -server -bootstrap-expect=1 -data-dir=consul-data -ui -bind=your ip address`

- Pour accéder à consul et voir l'ensemble des services actifs `localhost:8500/ui/dc1/services`


![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/009c06a9-d1c7-470b-9d75-f524c64a6faa)

- Après le lancement du micro-service config

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/27959b42-edfb-4f71-b1a2-47422e4c1d6f)


- Après le lancement de l'ensemble des services

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/ea255f81-4fc2-4f89-af4c-db5093bfddac)


#### La classe main ConfigServiceApplication

```java
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class ConfigServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConfigServiceApplication.class, args);
	}
}
```


#### Le fichier application.properties

```java
server.port=8888
spring.application.name=config-service
spring.cloud.config.server.git.uri=file:///C:/Users/hamid/Desktop/ecom-emsi/ecom-emsi/config-repo
```

#### Tests

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/92ecafdf-ef11-4c44-8a5e-2c6b52ad4aa3)




