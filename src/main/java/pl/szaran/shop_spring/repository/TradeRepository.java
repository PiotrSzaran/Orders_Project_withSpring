package pl.szaran.shop_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szaran.shop_spring.model.Trade;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
