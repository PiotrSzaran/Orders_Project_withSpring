package pl.szaran.shop_spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.szaran.shop_spring.service.TokenService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/security")
public class AccountActivationController {

    private final TokenService tokenService;

    @GetMapping("/account-activation")
    public String activateUser(@RequestParam String token) {
        tokenService.activateUser(token);
        return "redirect:/";
    }
}
