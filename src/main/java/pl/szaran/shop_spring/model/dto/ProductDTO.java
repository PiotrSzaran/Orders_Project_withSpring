package pl.szaran.shop_spring.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.szaran.shop_spring.model.enums.EGuarantee;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private CategoryDTO categoryDTO;
    private ProducerDTO producerDTO;
    private Set<EGuarantee> guarantees;
}
