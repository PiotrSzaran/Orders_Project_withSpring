package pl.szaran.shop_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szaran.shop_spring.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
