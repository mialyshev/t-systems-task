package com.javaschool.controller;

import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.entity.User;
import com.javaschool.repository.user.UserRepository;
import com.javaschool.service.user.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    private final UserRepository userRepository;

    @GetMapping
    public String getCardForm(Model model){
        model.addAttribute("cardForm", new CardRegisterDto());
        return "card-register";
    }

    @PostMapping
    public String registerNewCard(@ModelAttribute("cardForm") @Valid CardRegisterDto cardRegisterDto,
                                  @AuthenticationPrincipal UserDetails currentUser,
                                  BindingResult bindingResult,
                                  Model model){
        if (bindingResult.hasErrors()) {
            return "card-register";
        }

        User userFromBd = userRepository.findByEmail(currentUser.getUsername());

        String[] ownerDate = cardRegisterDto.getOwner().split(" ");
        if (ownerDate.length != 2){
            model.addAttribute("ownerError", "Cardholder data must consist of first and last name");
            return "card-register";
        }

        cardService.addCard(cardRegisterDto, userFromBd);
        return "index";
    }
}
