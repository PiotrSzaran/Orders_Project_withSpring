package pl.szaran.shop_spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.szaran.shop_spring.model.dto.CountryDTO;
import pl.szaran.shop_spring.service.CountryService;
import pl.szaran.shop_spring.validators.CountryValidator;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class CountryController {

    private final CountryService countryService;
    private final CountryValidator countryValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(countryValidator);
    }

    @GetMapping("/addCountry")
    public String addCountryGet(Model model) {

        model.addAttribute("country", new CountryDTO());
        model.addAttribute("errors", new HashMap<>());

        return "/admin/addCountry";
    }

    @PostMapping("/addCountry")
    public String addCountryPost(@Valid @ModelAttribute CountryDTO countryDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("country", new CountryDTO());
            var errors = bindingResult
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getCode, (v1, v2) -> v1 + ", " + v2));

            System.out.println(errors);

            model.addAttribute("errors", errors);
            return "/admin/addCountry";
        }

        countryService.addCountry(countryDTO);
        return "redirect:/admin/index";
    }
}