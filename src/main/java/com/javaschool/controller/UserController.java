package com.javaschool.controller;

import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.dto.order.OrderDto;
import com.javaschool.dto.user.UserDto;
import com.javaschool.dto.user.UserUpdateInfoDto;
import com.javaschool.dto.user.UserUpdatePassDto;
import com.javaschool.entity.User;
import com.javaschool.repository.user.UserRepository;
import com.javaschool.service.order.AddressService;
import com.javaschool.service.order.OrderService;
import com.javaschool.service.user.CardService;
import com.javaschool.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CardService cardService;
    private final AddressService addressService;
    private final OrderService orderService;

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
        return "redirect:/profile";
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
        return "redirect:/login";
    }

    @GetMapping("/orders")
    public String getAllOrders(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        User userFromBd = userService.getByEmail(currentUser);
        model.addAttribute("orders", orderService.findByUserId(userFromBd.getId()));
        return "user-orders";
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable("id") long id,
                           Model model){
        OrderDto orderDto = orderService.findById(id);
        model.addAttribute("address", addressService.getById(orderDto.getAddress_id()));
        model.addAttribute("order", orderDto);
        return "user-order";
    }

    @GetMapping("/order/payment/{id}")
    public String changePaymentType(@PathVariable("id") long id,
                                    Model model){
        orderService.changePayment(id);
        return "redirect:/profile/order/" + id;
    }

    @GetMapping("/order/pay/{id}")
    public String payForOrder(@PathVariable("id") long id,
                                    Model model){
        OrderDto orderDto = orderService.findById(id);
        model.addAttribute("orderForm", orderDto);
        model.addAttribute("savedCard", cardService.getAllByUserId(orderDto.getUser_id()));
        model.addAttribute("cardForm", new CardRegisterDto());
        return "user-pay-order";
    }

    @PostMapping("/savedcard/{id}")
    public String addCard(@PathVariable("id") long id,
                          @RequestParam(value = "cardId", required = true) long cardId){
        orderService.setPaid(id);
        return "redirect:/profile/order/" + id;
    }

    @PostMapping("/card/{id}")
    public String addCard(@PathVariable("id") long id,
                          @ModelAttribute("cardForm") @Valid CardRegisterDto cardRegisterDto,
                          @RequestParam(value = "save", required = false) String isSaved,
                          BindingResult bindingResult,
                          Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        User userFromBd = userService.getByEmail(currentUser);
        if(isSaved != null){
            String[] ownerDate = cardRegisterDto.getOwner().split(" ");
            if (ownerDate.length != 2){
                model.addAttribute("ownerError", "Cardholder data must consist of first and last name");
                return "card-register";
            }

            cardService.addCard(cardRegisterDto, userFromBd);
        }
        orderService.setPaid(id);
        return "redirect:/profile/order/" + id;
    }


    //TODO: Add edit addresses

}
