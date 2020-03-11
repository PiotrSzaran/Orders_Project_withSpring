package pl.szaran.shop_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szaran.shop_spring.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
