package pl.szaran.shop_spring.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.szaran.shop_spring.model.dto.CountryDTO;
import pl.szaran.shop_spring.repository.CountryRepository;

@Component
@RequiredArgsConstructor
public class CountryValidator implements Validator {

    private final CountryRepository countryRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(CountryDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

        CountryDTO countryDTO = (CountryDTO) o;

        if (countryDTO.getName() != null && countryDTO.getName().isEmpty()) {
            errors.rejectValue("name", "country name is empty");
        }

        if (countryDTO.getName() != null && !countryDTO.getName().matches("[A-Z\\s]+")) {
            errors.rejectValue("name", "should be uppercase");
        }

        if (countryRepository.findByName(countryDTO.getName()).isPresent()) {
            errors.rejectValue("name", "country already exists");
        }
    }
}
