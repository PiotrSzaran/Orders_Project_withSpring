package pl.szaran.shop_spring.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.szaran.shop_spring.model.dto.ProducerDTO;
import pl.szaran.shop_spring.repository.ProducerRepository;

@Component
@RequiredArgsConstructor
public class ProducerValidator implements Validator {

    private final ProducerRepository producerRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(ProducerDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ProducerDTO producerDTO = (ProducerDTO) o;

        if (producerDTO.getName() != null && producerDTO.getName().isEmpty()) {
            errors.rejectValue("name", "name is empty");
        }

        if (producerDTO.getName() != null && !producerDTO.getName().matches("[A-Z\\s]+")) {
            errors.rejectValue("name", "should be uppercase");
        }

        if (producerDTO.getCountryDTO().getName() != null && producerDTO.getCountryDTO().getName().isEmpty()) {
            errors.rejectValue("countryDTO.name", "country name is empty");
        }

        if (producerDTO.getCountryDTO().getName() != null && !producerDTO.getCountryDTO().getName().matches("[A-Z\\s]+")) {
            errors.rejectValue("countryDTO.name", "country name should be uppercase");
        }

        if (producerDTO.getTradeDTO().getName() != null && producerDTO.getTradeDTO().getName().isEmpty()) {
            errors.rejectValue("tradeDTO.name", "trade name is empty");
        }

        if (producerDTO.getTradeDTO().getName() != null && !producerDTO.getTradeDTO().getName().matches("[A-Z\\s]+")) {
            errors.rejectValue("tradeDTO.name", "trade name should be uppercase");
        }

        if (producerRepository.existsByNameAndCountry_NameAndTrade_Name(
                producerDTO.getName(),
                producerDTO.getCountryDTO().getName(),
                producerDTO.getTradeDTO().getName())) {

            errors.rejectValue("id", "trade already added");
        }
    }
}
