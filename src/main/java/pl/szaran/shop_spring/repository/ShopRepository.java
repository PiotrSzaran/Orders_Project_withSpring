package pl.szaran.shop_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szaran.shop_spring.model.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    boolean existsByNameAndCountry_Name(String name, String countryName);
}
