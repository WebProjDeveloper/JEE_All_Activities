package ma.emsi.customerfrontthymeleafapp.repository;

import ma.emsi.customerfrontthymeleafapp.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
