package pl.szaran.shop_spring.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.szaran.shop_spring.model.dto.UserDTO;
import pl.szaran.shop_spring.service.UserService;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UserDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

        UserDTO userDTO = (UserDTO) o;

        System.out.println("-------------WALIDACJA-------------");
        System.out.println(userDTO);
        System.out.println("-----------------------------------");

        if (userDTO.getUsername() != null && userDTO.getUsername().isEmpty()) {
            errors.rejectValue("username", "Username is empty");
        }

        if (userService.doesUserExists(userDTO.getUsername())) {
            errors.rejectValue("username", "Username already taken");
        }

        if (userDTO.getEmail() != null && userDTO.getEmail().isEmpty()) {
            errors.rejectValue("email", "Email is empty");
        }

        if (userService.doesEmailExists(userDTO.getEmail())) {
            errors.rejectValue("email", "Email already taken");
        }

        if (!userDTO.getEmail().contains("@")) {
            errors.rejectValue("email", "Email address is incorrect");
        }

        if (!userDTO.getPassword().equals(userDTO.getPasswordConfirmation())) {
            errors.rejectValue("password", "The given passwords are different");
        }

        if (userDTO.getPassword().length() < 8) {
            errors.rejectValue("password", "Password is too short! Should be at least 8 chars");
        }
    }
}
