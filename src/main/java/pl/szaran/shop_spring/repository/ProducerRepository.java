package pl.szaran.shop_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szaran.shop_spring.model.Producer;

public interface ProducerRepository extends JpaRepository<Producer, Long> {
}
