package pl.szaran.shop_spring.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.szaran.shop_spring.model.dto.TradeDTO;
import pl.szaran.shop_spring.repository.TradeRepository;

@Component
@RequiredArgsConstructor
public class TradeValidator implements Validator {

    private final TradeRepository tradeRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(TradeDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

        TradeDTO tradeDTO = (TradeDTO) o;

        if (tradeDTO.getName() != null && tradeDTO.getName().isEmpty()) {
            errors.rejectValue("name", "trade name is empty");
        }

        if (tradeDTO.getName() != null && !tradeDTO.getName().matches("[A-Z\\s]+")) {
            errors.rejectValue("name", "should be uppercase");
        }

        if (tradeRepository.findByName(tradeDTO.getName()).isPresent()) {
            errors.rejectValue("name", "trade already exists");
        }
    }
}
