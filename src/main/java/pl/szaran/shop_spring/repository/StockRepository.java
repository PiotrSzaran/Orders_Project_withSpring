package pl.szaran.shop_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szaran.shop_spring.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
