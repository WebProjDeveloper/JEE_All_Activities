package org.emsi.bankaccountservice;

import org.emsi.bankaccountservice.entities.BankAccount;
import org.emsi.bankaccountservice.entities.Customer;
import org.emsi.bankaccountservice.enums.AccountType;
import org.emsi.bankaccountservice.repositories.BankAccountRepository;
import org.emsi.bankaccountservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

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
