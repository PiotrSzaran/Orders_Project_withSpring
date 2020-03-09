package pl.szaran.shop_spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.szaran.shop_spring.validators.LoginValidator;

@Controller
@RequiredArgsConstructor
@RequestMapping("/security")
public class LoginController {

    private final LoginValidator loginValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {

        webDataBinder.addValidators(loginValidator);
    }

    @GetMapping("/login")
    public String loginGet() {
        return "security/login";
    }

    @GetMapping("/login/error")
    public String loginErrorGet(Model model) {
        model.addAttribute("error", "Login error");
        return "security/loginError";
    }

    @GetMapping("/accessDenied")
    public String accessDeniedHandler(Model model) {
        model.addAttribute("error", "Access denied!");
        return "security/accessDenied";
    }
}