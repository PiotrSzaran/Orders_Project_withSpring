package pl.szaran.shop_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szaran.shop_spring.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
