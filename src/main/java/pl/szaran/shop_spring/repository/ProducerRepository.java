package pl.szaran.shop_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szaran.shop_spring.model.Producer;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    boolean existsByNameAndCountry_NameAndTrade_Name(String name, String countryName, String tradeName);
}
