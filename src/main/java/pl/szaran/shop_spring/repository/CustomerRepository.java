package pl.szaran.shop_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szaran.shop_spring.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
