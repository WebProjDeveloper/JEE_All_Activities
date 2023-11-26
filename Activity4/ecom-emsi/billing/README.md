## Report for the micro-service billing


### La classe ConsulConfigRestController
```java
@RestController
public class ConsulConfigRestController {
    @Autowired
    private MyConsulConfig myConsulConfig;
    @Autowired
    private MyVaultConfig myVaultConfig;
    //@Value("${token.accessTokenTimeout}")
    //private long accessTokenTimeout;
    //@Value("${token.refreshTokenTimeout}")
    //private long refreshTokenTimeout;
    @GetMapping("/myConfig")
    public Map<String,Object> myConfig(){
        return Map.of("consulConfig",myConsulConfig, "vaultConfig",myVaultConfig);
    }
}
```

### La classe MyConsulConfig
```java
@Component
@ConfigurationProperties(prefix = "token")
@Data
public class MyConsulConfig {
    private long accessTokenTimeout;
    private long refreshTokenTimeout;
}
```

### La classe MyVaultConfig
```java
@Component
@ConfigurationProperties(prefix = "user")
@Data
public class MyVaultConfig {
    private String username;
    private String password;
    private String otp;
}
```

#### Main
```java
@SpringBootApplication
public class BillingApplication {
	@Autowired
	private VaultTemplate vaultTemplate;
	public static void main(String[] args) {
		SpringApplication.run(BillingApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(){
		return args -> {
			vaultTemplate.opsForVersionedKeyValue("secret")
					.put("keypair", Map.of("privKey","54321","pubKey","8999"));
		};
	};

}
```

#### Fichier application.properties
```java
spring.application.name=billing-service
server.port=8084
spring.config.import=optional:consul:, vault://
spring.cloud.vault.token=hvs.ruVleutswCOQcjv6pWJyQ7Tl
spring.cloud.vault.scheme=http
spring.cloud.vault.kv.enabled=true
management.endpoints.web.exposure.include=*
```

#### Tests
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/bf29554a-85af-442d-b0c4-795f6437e1d8)

