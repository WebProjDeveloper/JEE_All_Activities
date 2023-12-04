## Report for the micro-service ms-vault

#### La classe ConfigTestRestController
```java
@RestController
@RefreshScope
public class ConfigTestRestController {
    @Autowired
    private MyVaultSecretConfig myVaultSecretConfig;
    @Autowired
    private MyConsulConfig myConfig;

    @GetMapping("/config")
    public Map<String, Object> getConfig(){
        return Map.of("vault.user",myVaultSecretConfig,"consul.token", myConfig);
    }
}
```

#### La classe MyConsulConfig
```java
@Component
@ConfigurationProperties("token")
@Data
public class MyConsulConfig {
    private long timeout;
    private String authHeader;
}
```


#### La classe MyVaultSecretConfig
```java
@Component
@ConfigurationProperties("user")
@Data
public class MyVaultSecretConfig {
    private String username;
    private String password;
}
```


#### La classe MsVaultApplication
```java
@SpringBootApplication
public class MsVaultApplication {
    @Autowired
    private VaultTemplate vaultTemplate;
    public static void main(String[] args) {
        SpringApplication.run(MsVaultApplication.class, args);
    }
    @Bean
    CommandLineRunner start(VaultTemplate vaultTemplate){
        return args -> {
            Versioned.Metadata resp = vaultTemplate
                    .opsForVersionedKeyValue("secret")
                    .put("keypair", Map.of("privateKey","mySecret","publicKay","PK2"));
        };
    }
}
```

#### Fichier application.properties
```java
spring.application.name=vault-ms
spring.cloud.vault.token=hvs.ruVleutswCOQcjv6pWJyQ7Tl
spring.cloud.vault.scheme=http
spring.cloud.vault.kv.enabled=true
spring.config.import=optional:consul:, vault:// 
management.endpoints.web.exposure.include=*
```

#### Tests
+ Pour lancer vault `vault server -dev`
+ Pour accéder au vault `http://127.0.0.1:8200`
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/1ac687e1-d4b1-4b49-997b-ada6c863e7d5)

+ Les secrets
  
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/3336a1f8-2000-4c55-8c67-62aafe2c75bb)

+ Le secret billing-service

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/1d5a3063-0032-422a-8ab5-702478bf04bf)

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/ef31611c-d39c-4d35-af5a-e429642e5a60)




