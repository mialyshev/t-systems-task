package com.javaschool.controller;

import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.dto.order.AddressDto;
import com.javaschool.dto.user.UserUpdateInfoDto;
import com.javaschool.dto.user.UserUpdatePassDto;
import com.javaschool.service.order.AddressService;
import com.javaschool.service.order.OrderService;
import com.javaschool.service.user.CardService;
import com.javaschool.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CardService cardService;
    private final AddressService addressService;
    private final OrderService orderService;

    @GetMapping
    public String getAllUserInfo(Model model) {
        userService.getAllUserInfoController(model);
        return "user-page";
    }

    @GetMapping("/edit")
    public String getEditInfoPage(Model model) {
        userService.getEditInfoPageController(model);
        return "user-update";
    }

    @PostMapping("/edit")
    public String updateUserInfo(@ModelAttribute("userForm") @Valid UserUpdateInfoDto userUpdateDto,
                                 BindingResult bindingResult,
                                 Model model) {
        return userService.updateUserInfoController(userUpdateDto, bindingResult, model);
    }

    @GetMapping("/editpass")
    public String getEditPassPage(Model model) {
        userService.getEditPassPageController(model);
        return "user-update-password";
    }

    @PostMapping("/editpass")
    public String updateUserPass(@ModelAttribute("userForm") UserUpdatePassDto userUpdatePassDto,
                                 BindingResult bindingResult,
                                 Model model) {
        return userService.updateUserPassController(userUpdatePassDto, bindingResult, model);
    }


    @GetMapping("/addresses")
    public String getAllAddresses(Model model) {
        userService.getAllAddressesController(model);
        return "user-addresses";
    }

    @GetMapping("/address/delete/{id}")
    public String deleteAddress(@PathVariable("id") long id) {
        addressService.deleteAddress(id);
        return "redirect:/profile/addresses";
    }

    @GetMapping("/cards")
    public String getCards(Model model) {
        userService.getCardsController(model);
        return "user-cards";
    }

    @GetMapping("/orders")
    public String getAllOrders(Model model) {
        userService.getAllOrdersController(model);
        return "user-orders";
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable("id") long id,
                           Model model) {
        return userService.getOrderController(id, model);
    }

    @GetMapping("/order/payment/{id}")
    public String changePaymentType(@PathVariable("id") long id,
                                    Model model) {
        orderService.changePayment(id);
        return "redirect:/profile/order/" + id;
    }

    @GetMapping("/order/pay/{id}")
    public String getPayForOrderPage(@PathVariable("id") long id,
                                     Model model) {
        userService.getPayForOrderPageController(id, model);
        return "user-pay-order";
    }


    @GetMapping("/savedcard/{id}")
    public String payOrderBySavedCard(@PathVariable("id") long id) {
        orderService.setPaid(id);
        return "redirect:/profile/order/" + id;
    }

    @PostMapping("/card/{id}")
    public String payOrderByCard(@PathVariable("id") long id,
                                 @ModelAttribute("cardForm") @Valid CardRegisterDto cardRegisterDto,
                                 BindingResult bindingResult,
                                 @RequestParam(value = "save", required = false) String isSaved,
                                 Model model) {
        return userService.payOrderByCardController(id, cardRegisterDto, bindingResult, isSaved, model);
    }


    @GetMapping("/address/edit/{id}")
    public String getPageForEditAddress(@PathVariable("id") long id,
                                        Model model) {
        return userService.getPageForEditAddressController(id, model);
    }

    @PostMapping("/address/edit")
    public String editAddress(@ModelAttribute("addressForm") @Valid AddressDto addressDto,
                              BindingResult bindingResult) {
        return userService.editAddressController(addressDto, bindingResult);
    }


    @GetMapping("/orders/delivered")
    public String getDeliveredOrders(Model model) {
        userService.getDeliveredOrdersController(model);
        return "user-delivered-orders";
    }
}
