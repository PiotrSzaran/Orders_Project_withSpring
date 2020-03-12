package pl.szaran.shop_spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.szaran.shop_spring.model.dto.CustomerDTO;
import pl.szaran.shop_spring.service.CustomerService;
import pl.szaran.shop_spring.validators.CustomerValidator;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerValidator customerValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(customerValidator);
    }

    @GetMapping("/addCustomer")
    public String addCustomerGet(Model model) {

        model.addAttribute("customer", new CustomerDTO());
        model.addAttribute("errors", new HashMap<>());

        return "/admin/addCustomer";
    }

    @PostMapping("/addCustomer")
    public String addCustomerPost(@Valid @ModelAttribute CustomerDTO customerDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("customer", new CustomerDTO());
            var errors = bindingResult
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getCode, (v1, v2) -> v1 + ", " + v2));

            System.out.println(errors);

            model.addAttribute("errors", errors);
            return "/admin/addCustomer";
        }

        customerService.addCustomer(customerDTO);
        return "redirect:/admin/index";
    }
}