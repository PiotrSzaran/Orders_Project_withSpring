package pl.szaran.shop_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szaran.shop_spring.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
