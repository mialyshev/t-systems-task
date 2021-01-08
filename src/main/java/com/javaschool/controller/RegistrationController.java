package com.javaschool.controller;

import com.javaschool.dto.user.UserRegistrationDto;
import com.javaschool.service.user.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Data
@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping
    public String getRegistrationForm(Model model){
        model.addAttribute("userRegister", new UserRegistrationDto());
        return "registration";
    }

    @PostMapping
    public String registerNewUser(@ModelAttribute("userRegister") @Valid UserRegistrationDto userRegistrationDto,
                                  BindingResult bindingResult,
                                  Model model){
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if(userService.getByEmail(userRegistrationDto.getEmail()) != null){
            model.addAttribute("userError", "User with this email is already registered");
            return "registration";
        }

        if(!userRegistrationDto.getEmail().equals(userRegistrationDto.getConfirmEmail())){
            model.addAttribute("emailError", "Email does not match");
            return "registration";
        }

        if(!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmPassword())){
            model.addAttribute("passwordError", "Password does not match");
            return "registration";
        }

        userService.registerUser(userRegistrationDto);
        return "index";
    }
}
