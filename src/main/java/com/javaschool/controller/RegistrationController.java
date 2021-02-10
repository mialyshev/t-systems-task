package com.javaschool.controller;

import com.javaschool.dto.user.UserRegistrationDto;
import com.javaschool.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping
    public String getRegistrationForm(Model model) {
        return userService.getRegistrationFormController(model);
    }

    @PostMapping
    public String registerNewUser(@ModelAttribute("userRegister") @Valid UserRegistrationDto userRegistrationDto,
                                  BindingResult bindingResult,
                                  Model model) {
        return userService.registerNewUserController(bindingResult, userRegistrationDto, model);
    }
}
