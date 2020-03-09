package pl.szaran.shop_spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.szaran.shop_spring.service.UserService;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    //domyślna strona główna
    @GetMapping({"/index", "/"})
    public String index() {

        return "index";
    }

    //domyślna strona główna po zalogowaniu z rolą ADMIN
    @GetMapping("admin/index")
    public String adminIndex(Model model){
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final UserDetails userDetails = (UserDetails) auth.getPrincipal();

        model.addAttribute("admin", userService.findByUsername(userDetails.getUsername()));

        return "/admin/index";
    }
}