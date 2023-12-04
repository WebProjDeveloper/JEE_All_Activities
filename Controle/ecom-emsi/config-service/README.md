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

- Par défaut

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/8cc8af77-ed47-4837-a776-b0a5372abe78)

- En dev

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/7f32796a-a24d-49b2-99e5-0143733cf9ca)

- En prod

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/aa27dad1-e602-49cc-a988-fba674bf86a8)







