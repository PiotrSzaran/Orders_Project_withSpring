package pl.szaran.shop_spring.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProducerDTO {
    private Long id;
    private String name;
    private CountryDTO countryDTO;
    private TradeDTO tradeDTO;
}
