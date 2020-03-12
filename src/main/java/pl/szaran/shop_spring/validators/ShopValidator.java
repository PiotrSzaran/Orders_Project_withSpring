package pl.szaran.shop_spring.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.szaran.shop_spring.model.dto.ShopDTO;
import pl.szaran.shop_spring.repository.ShopRepository;

@Component
@RequiredArgsConstructor
public class ShopValidator implements Validator {

    private final ShopRepository shopRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(ShopDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ShopDTO shopDTO = (ShopDTO) o;

        if (shopDTO.getName() != null && shopDTO.getName().isEmpty()) {
            errors.rejectValue("name", "name is empty");
        }

        if (shopDTO.getName() != null && !shopDTO.getName().matches("[A-Z\\s]+")) {
            errors.rejectValue("name", "should be uppercase");
        }

        if (shopDTO.getCountryDTO().getName() != null && shopDTO.getCountryDTO().getName().isEmpty()) {
            errors.rejectValue("countryDTO.name", "country name is empty");
        }

        if (shopDTO.getCountryDTO().getName() != null && !shopDTO.getCountryDTO().getName().matches("[A-Z\\s]+")) {
            errors.rejectValue("countryDTO.name", "country name should be uppercase");
        }

        if (shopRepository.existsByNameAndCountry_Name(
                shopDTO.getName(),
                shopDTO.getCountryDTO().getName())) {

            errors.rejectValue("id", "shop already added");
        }

    }
}
