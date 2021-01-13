package com.javaschool.controller;

import com.javaschool.dto.order.AddressAdditionDto;
import com.javaschool.entity.User;
import com.javaschool.repository.user.UserRepository;
import com.javaschool.service.order.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final UserRepository userRepository;

    @GetMapping
    public String getAddressForm(Model model){
        model.addAttribute("addressForm", new AddressAdditionDto());
        return "address";
    }

    @PostMapping
    public String addNewAddress(@ModelAttribute("addressForm") @Valid AddressAdditionDto addressAdditionDto,
                                BindingResult bindingResult,
                                Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        User userFromBd = userRepository.findByEmail(currentUser);
        addressAdditionDto.setSaved(true);
        addressService.addAddress(addressAdditionDto, userFromBd);
        return "redirect:/user";
    }
}
