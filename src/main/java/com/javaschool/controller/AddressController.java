package com.javaschool.controller;

import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.dto.order.AddressAdditionDto;
import com.javaschool.dto.order.AddressDto;
import com.javaschool.entity.User;
import com.javaschool.repository.user.UserRepository;
import com.javaschool.service.order.AddressService;
import com.javaschool.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final UserRepository userRepository;

    @GetMapping
    public String getAddressForm(Model model){
        AddressDto lastAddress = addressService.getLast();
        model.addAttribute("lastAddress", lastAddress);
        model.addAttribute("addressForm", new AddressAdditionDto());
        return "address";
    }

    @PostMapping
    public String addNewAddress(@ModelAttribute("addressForm") @Valid AddressAdditionDto addressAdditionDto,
                                @RequestParam(value = "save", required = false) String isSaved,
                                @AuthenticationPrincipal UserDetails currentUser,
                                BindingResult bindingResult,
                                Model model){

        User userFromBd = userRepository.findByEmail(currentUser.getUsername());
        if(isSaved != null){
            addressAdditionDto.setSaved(true);
        }else {
            addressAdditionDto.setSaved(false);
        }
        addressService.addAddress(addressAdditionDto, userFromBd);
        return "redirect:/address";
    }
}
