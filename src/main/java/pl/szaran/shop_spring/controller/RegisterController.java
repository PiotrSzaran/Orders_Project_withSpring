package pl.szaran.shop_spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.szaran.shop_spring.model.dto.UserDTO;
import pl.szaran.shop_spring.model.enums.Role;
import pl.szaran.shop_spring.service.UserService;
import pl.szaran.shop_spring.validators.UserValidator;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/security")
public class RegisterController {

    private final UserService userService;
    private final UserValidator userValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(userValidator);
    }

    @GetMapping("/register")
    public String registerGet(Model model) {
        UserDTO userDTO = new UserDTO();
        userDTO.setRole(Role.USER);

        model.addAttribute("user", userDTO);
        model.addAttribute("errors", new HashMap<>());

        return "/security/register";
    }

    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            UserDTO newUserDTO = new UserDTO();
            newUserDTO.setRole(Role.USER);

            model.addAttribute("user", userDTO);

            var errors = bindingResult
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getCode, (v1, v2) -> v1 + ", " + v2));

            System.out.println(errors);

            model.addAttribute("errors", errors);
            return "/security/register";
        }

        //userService.add(userDTO); //todo dorobić metodę, która wysle do usera mail z linkiem aktywacyjnym (token)

        return "redirect:/";
    }
}
