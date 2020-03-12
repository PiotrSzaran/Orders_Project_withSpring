package pl.szaran.shop_spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.szaran.shop_spring.model.dto.ShopDTO;
import pl.szaran.shop_spring.service.ShopService;
import pl.szaran.shop_spring.validators.ShopValidator;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ShopController {

    private final ShopService shopService;
    private final ShopValidator shopValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(shopValidator);
    }

    @GetMapping("/addShop")
    public String addShopGet(Model model) {

        model.addAttribute("shop", new ShopDTO());
        model.addAttribute("errors", new HashMap<>());

        return "/admin/addShop";
    }

    @PostMapping("/addShop")
    public String addShopPost(@Valid @ModelAttribute ShopDTO shopDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("shop", new ShopDTO());
            var errors = bindingResult
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getCode, (v1, v2) -> v1 + ", " + v2));

            System.out.println(errors);

            model.addAttribute("errors", errors);
            return "/admin/addShop";
        }

        shopService.addShop(shopDTO);
        return "redirect:/admin/index";
    }
}