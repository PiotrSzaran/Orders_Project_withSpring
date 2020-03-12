package pl.szaran.shop_spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.szaran.shop_spring.model.dto.TradeDTO;
import pl.szaran.shop_spring.service.TradeService;
import pl.szaran.shop_spring.validators.TradeValidator;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class TradeController {

    private final TradeService tradeService;
    private final TradeValidator tradeValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(tradeValidator);
    }

    @GetMapping("/addTrade")
    public String addTradeGet(Model model) {

        model.addAttribute("trade", new TradeDTO());
        model.addAttribute("errors", new HashMap<>());

        return "/admin/addTrade";
    }

    @PostMapping("/addTrade")
    public String addTradePost(@Valid @ModelAttribute TradeDTO tradeDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("trade", new TradeDTO());
            var errors = bindingResult
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getCode, (v1, v2) -> v1 + ", " + v2));

            System.out.println(errors);

            model.addAttribute("errors", errors);
            return "/admin/addTrade";
        }

        tradeService.addTrade(tradeDTO);
        return "redirect:/admin/index";
    }
}