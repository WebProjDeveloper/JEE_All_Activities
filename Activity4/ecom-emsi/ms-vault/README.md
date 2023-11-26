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
