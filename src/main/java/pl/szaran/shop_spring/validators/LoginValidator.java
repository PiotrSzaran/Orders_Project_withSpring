package pl.szaran.shop_spring.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.szaran.shop_spring.model.dto.UserDTO;
import pl.szaran.shop_spring.service.UserService;


@Component
@RequiredArgsConstructor
public class LoginValidator implements Validator {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UserDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDTO userDTO = (UserDTO) o;

        //sprawdzamy czy istnieje taki login
        if (!userService.doesUserExists(userDTO.getUsername())) {
            errors.rejectValue("username", "Username doesn't exists");
        }

        //sprawdzamy czy hasło podane przy logowaniu zgadza się z tym w bazie
        if (!passwordEncoder.matches(userDTO.getPassword(), userService.findByUsername(userDTO.getUsername()).getPassword())) {
            errors.rejectValue("password", "Password is incorrect");
        }

    }
}
