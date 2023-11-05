# Activity 2 : Création d'un micro service 

## Objectif de l'activité
Création d'un micro service qui permet de gérer les comptes bancaires

### Les enums
- L'enumeration AccountType
```java 
public enum AccountType {
    CURRENT_ACCOUNT, SAVING_ACCOUNT
}
```

### Les entités JPA 
- La classe BankAccount
```java 
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BankAccount {
    @Id
    private String id;
    private Date createdAt;
    private Double balance;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    @ManyToOne
    private Customer customer;
}
```

- L'interface AccountProjection
```java 
@Projection(types = BankAccount.class, name = "p1")
public interface AccountProjection {
    public String getId();
    public AccountType getType();
    public Double getBalance();
}
```

- La classe Customer
```java   
@Entity
@NoArgsConstructor @AllArgsConstructor @Data @Builder
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "customer")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<BankAccount> bankAccounts;
}
```

### Les repositories
- L'interface BankAccountRepository 
```java 
@RepositoryRestResource
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    @RestResource(path = "/byType")
    List<BankAccount> findByType(@Param("t") AccountType type);
}
```

- L'interface CustomerRepository
```java
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
```

### Les exceptions
- La classe CustomDataFetcherExceptionResolver
```java
@Component
public class CustomDataFetcherExceptionResolver extends DataFetcherExceptionResolverAdapter {
   @Override
   protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
      return new GraphQLError() {
          @Override
          public String getMessage() {
              return ex.getMessage();
          }
          @Override
          public List<SourceLocation> getLocations() {
              return null;
          }
          @Override
          public ErrorClassification getErrorType() {
              return null;
          }
      };
   }
}
```

### Les dtos
- La classe BankAccountRequestDTO
```java 
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BankAccountRequestDTO {
    private Double balance;
    private String currency;
    private AccountType type;
}
```

- La classe BankAccountResponseDTO
```java
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BankAccountResponseDTO {
    private String id;
    private Date createdAt;
    private Double balance;
    private String currency;
    private AccountType type;
}
```

### Les mappers
- La classe AccountMapper
```java
@Component
public class AccountMapper {
    public BankAccountResponseDTO fromBankAccount(BankAccount bankAccount){
        BankAccountResponseDTO bankAccountResponseDTO = new BankAccountResponseDTO();
        BeanUtils.copyProperties(bankAccount, bankAccountResponseDTO);
        return bankAccountResponseDTO;
    }
}
```

### La couche service
- L'interface AccountService
```java
  public interface AccountService  {
    BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDTO);

    BankAccountResponseDTO updateAccount(String id, BankAccountRequestDTO bankAccountDTO);
}
```
- La classe AccountServiceImpl
```java
@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountMapper accountMapper;
    @Override
    public BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDTO) {
        BankAccount bankAccount = BankAccount.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(new Date())
                .balance(bankAccountDTO.getBalance())
                .type(bankAccountDTO.getType())
                .currency(bankAccountDTO.getCurrency())
                .build();
        BankAccount saveBankAccount = bankAccountRepository.save(bankAccount);
        /*BankAccountResponseDTO bankAccountResponseDTO = BankAccountResponseDTO.builder()
                .id(saveBankAccount.getId())
                .createdAt(saveBankAccount.getCreatedAt())
                .balance(saveBankAccount.getBalance())
                .type(saveBankAccount.getType())
                .currency(saveBankAccount.getCurrency())
                .build();*/
        BankAccountResponseDTO bankAccountResponseDTO = accountMapper.fromBankAccount(saveBankAccount);
        return bankAccountResponseDTO;
    }
    @Override
    public BankAccountResponseDTO updateAccount(String id, BankAccountRequestDTO bankAccountDTO) {
        BankAccount bankAccount = BankAccount.builder()
                .id(id)
                .createdAt(new Date())
                .balance(bankAccountDTO.getBalance())
                .type(bankAccountDTO.getType())
                .currency(bankAccountDTO.getCurrency())
                .build();
        BankAccount saveBankAccount = bankAccountRepository.save(bankAccount);
        BankAccountResponseDTO bankAccountResponseDTO = accountMapper.fromBankAccount(saveBankAccount);
        return bankAccountResponseDTO;
    }
}
```
### La couche web 
- La classe AccountRestController
```java
@RestController
@RequestMapping("/api")
public class AccountRestController {
    private BankAccountRepository bankAccountRepository;
    private AccountService accountService;
    private AccountMapper accountMapper;
    //Injection des dépendances
    public AccountRestController(BankAccountRepository bankAccountRepository, AccountService accountService, AccountMapper accountMapper){
        this.bankAccountRepository = bankAccountRepository;
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }
    @GetMapping("/bankAccounts")
    public List<BankAccount> bankAccounts(){
        return bankAccountRepository.findAll();
    }
    @GetMapping("/bankAccounts/{id}")
    public BankAccount bankAccount(@PathVariable String id){
        return bankAccountRepository.findById(id)
                .orElseThrow(()->new RuntimeException(String.format("Account ùs not found", id)));
    }
    @PostMapping("/bankAccounts")
    public BankAccountResponseDTO save(@RequestBody BankAccountRequestDTO requestDTO){
        return accountService.addAccount(requestDTO);
    }
    @PutMapping("/bankAccounts/{id}")
    public BankAccount update(@PathVariable String id, @RequestBody BankAccount bankAccount){
        BankAccount account = bankAccountRepository.findById(id).orElseThrow();
        if(bankAccount.getBalance()!=null) account.setBalance(bankAccount.getBalance());
        if(bankAccount.getCreatedAt()!=null) account.setCreatedAt(new Date());
        if(bankAccount.getType()!=null) account.setType(bankAccount.getType());
        if(bankAccount.getCurrency()!=null) account.setCurrency(bankAccount.getCurrency());
        return bankAccountRepository.save(account);
    }
    @DeleteMapping("/bankAccounts/{id}")
    public void deleteAccount(@PathVariable String id){
        bankAccountRepository.deleteById(id);
    }
}
```

- La classe BankAccountGraphQLController
```java
@Controller
public class BankAccountGraphQLController {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerRepository customerRepository;
    @QueryMapping
    public List<BankAccount> accountsList(){
        return bankAccountRepository.findAll();
    }
    @QueryMapping
    public BankAccount bankAccountById(@Argument String id){
        return bankAccountRepository.findById(id)
                .orElseThrow(()->new RuntimeException(String.format("Account %s not found", id)));
    }
    @MutationMapping
    public BankAccountResponseDTO addAccount(@Argument BankAccountRequestDTO bankAccount){
        return accountService.addAccount(bankAccount);
    }
    @MutationMapping
    public BankAccountResponseDTO updateAccount(@Argument String id, @Argument BankAccountRequestDTO bankAccount){
        return accountService.updateAccount(id,bankAccount);
    }
    @MutationMapping
    public void deleteAccount(@Argument String id){
       bankAccountRepository.deleteById(id);
    }
    @QueryMapping
    public List<Customer> customers(){
        return customerRepository.findAll();
    }
}
/*
@Data
@NoArgsConstructor @AllArgsConstructor
class BankAccountDTO {
    private String type;
    private Double balance;
    private String currency;
}

record BankAccountDTO(Double balance, String type, String currency){
}*/
```
### La classe Main
- La classe BankAccountServiceApplication
```java
@SpringBootApplication
public class BankAccountServiceApplication {
	public static void main(String[] args) {

		SpringApplication.run(BankAccountServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository){
		return args -> {
			Stream.of("Housna", "Hayat", "Meriem", "Ahmed").forEach(c->{
				Customer customer = Customer.builder()
								.name(c)
						        .build();
				customerRepository.save(customer);

			});
			customerRepository.findAll().forEach(customer -> {
				for (int i = 0; i < 10; i++){
					BankAccount bankAccount = BankAccount.builder()
							.id(UUID.randomUUID().toString())
							.type(Math.random()>0.5? AccountType.CURRENT_ACCOUNT:AccountType.SAVING_ACCOUNT)
							.balance(10000+Math.random()*90000)
							.createdAt(new Date())
							.currency("MAD")
							.customer(customer)
							.build();
					bankAccountRepository.save(bankAccount);
				}
			});
		};
	}
}
```
### Le fichier schema
- schema.graphqls
```java
type Query {
    accountsList : [BankAccount],
    bankAccountById (id:String) : BankAccount
    customers : [Customer]
}
type Mutation {
    addAccount(bankAccount : BankAccountDTO) : BankAccount,
    updateAccount(id : String, bankAccount : BankAccountDTO) : BankAccount,
    deleteAccount(id:String) : String
}
type Customer {
    id : ID,
    name : String,
    bankAccounts : [BankAccount]
}
type BankAccount {
    id : String,
    createdAt : Float,
    balance : Float,
    currency : String,
    type : String,
    customer : Customer
}
input BankAccountDTO {
     balance : Float,
     currency : String,
     type : String
}
```
- Le fichier application.properties
```java
spring.datasource.url=jdbc:h2:mem:account-db
spring.h2.console.enabled=true
server.port=8081
spring.graphql.graphiql.enabled=true
```

- Le fichier pom.xml
```java
<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-rest</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-graphql</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-webflux</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.graphql</groupId>
			<artifactId>spring-graphql-test</artifactId>
			<scope>test</scope>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-ui -->
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.2.0</version>
		</dependency>
	</dependencies>
```
### La partie Test
Lorsqu'on tape ce lien `localhost:8081/bankAccounts` la liste des comptes va s'afficher

<img width="712" alt="Capture" src="https://github.com/WebProjDeveloper/Activities_Repos/assets/125798807/8e0a5dcb-b9e4-4f9c-b36a-6381b3cf473a">

- Swagger

<img width="949" alt="Screenshot 2023-10-24 153732" src="https://github.com/WebProjDeveloper/Activities_Repos/assets/125798807/1bbb53eb-ba38-4704-9657-02c5c6502e25">

- Insomnia
- GraphQL
Pour récuperer la liste des comptes :
```java
query {
  accountsList{
    id
  }
}
```
Pour ajouter un compte bancaire :
```java
mutation ($t : String, $b : Float, $c : String) {
  addAccount(bankAccount : {
    type : $t,
    balance : $b ,
    currency : $c 
  }
  ){
    id, type, balance
  }
}
```
```java
{"t" : "CURRENT_ACCOUNT", "b" : 98000, "c" : "MAD"}
```
Pour modifier un compte bancaire :
```java
mutation ($id : String, $t : String, $b : Float, $c : String) {
  updateAccount(
    id : $id,
    bankAccount : {
    type : $t,
    balance : $b ,
    currency : $c 
  }
  ){
    id, type, balance
  }
}
```
```java
{"t" : "CURRENT_ACCOUNT", "b" : 5000, "c" : "MAD", "id":"6f6534a5-b70f-4501-bde2-299755b60256"}
```

