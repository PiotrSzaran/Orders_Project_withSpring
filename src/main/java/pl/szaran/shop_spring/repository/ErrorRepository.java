package pl.szaran.shop_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szaran.shop_spring.model.Error;

public interface ErrorRepository extends JpaRepository<Error, Long> {

}
