package com.javaschool.controller;

import com.javaschool.dto.user.UserDto;
import com.javaschool.dto.user.UserUpdateInfoDto;
import com.javaschool.dto.user.UserUpdatePassDto;
import com.javaschool.entity.User;
import com.javaschool.service.order.AddressService;
import com.javaschool.service.user.CardService;
import com.javaschool.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CardService cardService;
    private final AddressService addressService;

    @GetMapping
    public String getAllUserInfo(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        UserDto userFromBd = userService.getDtoByEmail(currentUser);
        model.addAttribute("userInfo", userFromBd);
        model.addAttribute("savedCard", cardService.getAllByUserId(userFromBd.getId()));
        model.addAttribute("savedAddress", addressService.getAllSaved(userFromBd.getId()));
        return "user-page";
    }

    @GetMapping("/edit")
    public String editUserInfo(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        UserDto userFromBd = userService.getDtoByEmail(currentUser);
        model.addAttribute("userForm", userService.getUserUpdateDto(userFromBd));
        return "user-update";
    }

    @PostMapping("/edit")
    public String updateUserInfo(@ModelAttribute("userForm") UserUpdateInfoDto userUpdateDto,
                                 Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        if(!currentUser.equals(userUpdateDto.getEmail())){
            if(userService.getByEmail(userUpdateDto.getEmail()) != null){
                model.addAttribute("userError", "User with this email is already registered");
                return "user-update";
            }
        }
        if(!userUpdateDto.getEmail().equals(userUpdateDto.getConfirmEmail())){
            model.addAttribute("emailError", "Email does not match");
            return "user-update";
        }
        userService.updateUserInfo(userUpdateDto);
        return "redirect:/user";
    }

    @GetMapping("/editpass")
    public String editUserPass(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        UserDto userFromBd = userService.getDtoByEmail(currentUser);
        model.addAttribute("userForm", userService.getUserUpdatePass(userFromBd));
        return "user-update-password";
    }

    @PostMapping("/editpass")
    public String updateUserPass(@ModelAttribute("userForm") UserUpdatePassDto userUpdatePassDto,
                                 Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        User userFromBd = userService.getByEmail(currentUser);
        if(!userService.checkMatch(userUpdatePassDto, userFromBd.getPassword())){
            model.addAttribute("userForm", userService.getUserUpdatePass(userService.toDto(userFromBd)));
            model.addAttribute("oldPassError", "Old password is incorrect");
            return "user-update-password";
        }

        if(!userService.updateUserPass(userUpdatePassDto)){
            model.addAttribute("userForm", userService.getUserUpdatePass(userService.toDto(userFromBd)));
            model.addAttribute("passwordError", "Password does not match");
            return "user-update-password";
        }
        return "/login";
    }
}
