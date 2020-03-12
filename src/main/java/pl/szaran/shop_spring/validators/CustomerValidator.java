package pl.szaran.shop_spring.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.szaran.shop_spring.model.dto.CustomerDTO;
import pl.szaran.shop_spring.repository.CustomerRepository;

@Component
@RequiredArgsConstructor
public class CustomerValidator implements Validator {

    private final CustomerRepository customerRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(CustomerDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CustomerDTO customerDTO = (CustomerDTO) o;

        if (customerDTO.getName() != null && customerDTO.getName().isEmpty()) {
            errors.rejectValue("name", "name is empty");
        }

        if (customerDTO.getName() != null && !customerDTO.getName().matches("[A-Z\\s]+")) {
            errors.rejectValue("name", "should be uppercase");
        }

        if (customerDTO.getSurname() != null && customerDTO.getSurname().isEmpty()) {
            errors.rejectValue("surname", "surname is empty");
        }

        if (customerDTO.getSurname() != null && !customerDTO.getSurname().matches("[A-Z\\s]+")) {
            errors.rejectValue("surname", "should be uppercase");
        }

        if (customerDTO.getAge() == null || customerDTO.getAge() < 18)
            errors.rejectValue("age", "should be adult");

        if (customerDTO.getCountryDTO().getName() != null && customerDTO.getCountryDTO().getName().isEmpty()) {
            errors.rejectValue("countryDTO.name", "country name is empty");
        }

        if (customerDTO.getCountryDTO().getName() != null && !customerDTO.getCountryDTO().getName().matches("[A-Z\\s]+")) {
            errors.rejectValue("countryDTO.name", "country name should be uppercase");
        }

        if (customerRepository.existsCustomerByAgeAndCountry_NameAndNameAndSurname(
                customerDTO.getAge(),
                customerDTO.getCountryDTO().getName(),
                customerDTO.getName(),
                customerDTO.getSurname())) {
            errors.rejectValue("id", "customer already added");
        }

    }
}
