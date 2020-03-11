package pl.szaran.shop_spring.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.szaran.shop_spring.model.dto.CategoryDTO;

@Component
@RequiredArgsConstructor
public class CategoryValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(CategoryDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

        CategoryDTO categoryDTO = (CategoryDTO) o;

        if (categoryDTO.getName() != null && categoryDTO.getName().isEmpty()) {
            errors.rejectValue("category", "category name is empty");
        }

        if (categoryDTO.getName() != null && !categoryDTO.getName().matches("[A-Z\\s]+")) {
            errors.rejectValue("category", "name is incorrect: " + categoryDTO.getName());
        }
    }
}