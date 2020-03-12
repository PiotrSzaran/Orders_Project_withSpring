package pl.szaran.shop_spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.szaran.shop_spring.model.dto.ProducerDTO;
import pl.szaran.shop_spring.service.ProducerService;
import pl.szaran.shop_spring.validators.ProducerValidator;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ProducerController {

    private final ProducerService producerService;
    private final ProducerValidator producerValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(producerValidator);
    }

    @GetMapping("/addProducer")
    public String addProducerGet(Model model) {

        model.addAttribute("producer", new ProducerDTO());
        model.addAttribute("errors", new HashMap<>());

        return "/admin/addProducer";
    }

    @PostMapping("/addProducer")
    public String addProducerPost(@Valid @ModelAttribute ProducerDTO producerDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("producer", new ProducerDTO());
            var errors = bindingResult
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getCode, (v1, v2) -> v1 + ", " + v2));

            System.out.println(errors);

            model.addAttribute("errors", errors);
            return "/admin/addProducer";
        }

        producerService.addProducer(producerDTO);
        return "redirect:/admin/index";
    }
}