package pl.szaran.shop_spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.szaran.shop_spring.model.dto.CategoryDTO;
import pl.szaran.shop_spring.service.CategoryService;
import pl.szaran.shop_spring.validators.CategoryValidator;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryValidator categoryValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(categoryValidator);
    }

    @GetMapping("/addCategory")
    public String addCategoryGet(Model model) {

        model.addAttribute("category", new CategoryDTO());
        model.addAttribute("errors", new HashMap<>());

        return "/admin/addCategory";
    }

    @PostMapping("/addCategory")
    public String addCategoryPost(@Valid @ModelAttribute CategoryDTO categoryDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("category", new CategoryDTO());
            var errors = bindingResult
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getCode, (v1, v2) -> v1 + ", " + v2));

            System.out.println(errors);

            model.addAttribute("errors", errors);
            return "/admin/addCategory";
        }

        categoryService.addCategory(categoryDTO);
        return "redirect:/admin/index";
    }
}