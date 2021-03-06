package com.javaschool.controller;

import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.service.user.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @GetMapping("/profile/add-card")
    public String getCardForm(Model model) {
        model.addAttribute("cardForm", new CardRegisterDto());
        return "card-register";
    }

    @PostMapping("/profile/add-card")
    public String registerNewCard(@ModelAttribute("cardForm") @Valid CardRegisterDto cardRegisterDto,
                                  BindingResult bindingResult,
                                  Model model) {
        return cardService.registerNewCardController(bindingResult, cardRegisterDto, model);
    }
}
