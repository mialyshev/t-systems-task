package com.javaschool.controller;

import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.dto.order.AddressAdditionDto;
import com.javaschool.dto.order.AddressDto;
import com.javaschool.dto.order.OrderDto;
import com.javaschool.dto.product.ProductDto;
import com.javaschool.entity.User;
import com.javaschool.repository.user.UserRepository;
import com.javaschool.service.order.AddressService;
import com.javaschool.service.order.OrderService;
import com.javaschool.service.product.ProductService;
import com.javaschool.service.user.CardService;
import com.javaschool.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
@SessionAttributes(types = {OrderDto.class})
public class OrderController {
    private final OrderService orderService;
    private final UserRepository userRepository;
    private final AddressService addressService;
    private final CardService cardService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping
    public String getOrderForm(Model model, @RequestParam(value = "selected", required = false)Integer[] selected){
        if(selected == null){
            model.addAttribute("orderError", "Please select at least one product");
            return "bucket";
        }
        OrderDto orderDto = new OrderDto();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userFromBd = userRepository.findByEmail(authentication.getName());
        orderDto.setProductDtoList(productService.getSelectedList(selected));
        orderDto.setUser_id(userFromBd.getId());
        model.addAttribute("savedAddress", addressService.getAllSaved(orderDto.getUser_id()));
        model.addAttribute("addressForm", new AddressAdditionDto());
        model.addAttribute("orderForm", orderDto);
        return "order-address";
    }


    @PostMapping("/address")
    public String addAddress(@ModelAttribute("orderForm") OrderDto orderDto,
                              @ModelAttribute("addressForm") @Valid AddressAdditionDto addressAdditionDto,
                              BindingResult bindingResult,
                              @RequestParam(value = "save", required = false) String isSaved,
                              Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("savedAddress", addressService.getAllSaved(orderDto.getUser_id()));
            model.addAttribute("orderForm", orderDto);
            return "order-address";
        }
        if(isSaved != null){
            addressAdditionDto.setSaved(true);
        }else {
            addressAdditionDto.setSaved(false);
        }
        addressService.addAddress(addressAdditionDto, userRepository.findById(orderDto.getUser_id()));
        orderDto.setAddress_id(addressService.getLastByUserId(orderDto.getUser_id()).getId());
        model.addAttribute("paymentType", orderService.getPaymentTypeList());
        return "order-choose-pay";
    }


    @PostMapping("/savedaddress")
    public String addAddress(@ModelAttribute("orderForm") OrderDto orderDto,
                             Model model){
        model.addAttribute("paymentType", orderService.getPaymentTypeList());
        return "order-choose-pay";
    }

    @PostMapping("/payment")
    public String getPaymentType(@ModelAttribute("orderForm") OrderDto orderDto,
                                 Model model){
        if(orderDto.getPaymentType().equals("CARD")) {
            model.addAttribute("savedCard", cardService.getAllByUserId(orderDto.getUser_id()));
            model.addAttribute("cardForm", new CardRegisterDto());
            return "order-card";
        }
        model.addAttribute("address", addressService.getById(orderDto.getAddress_id()));
        return "order-finish";
    }

    @PostMapping("/card")
    public String addCard(@ModelAttribute("orderForm") OrderDto orderDto,
                          @ModelAttribute("cardForm") @Valid CardRegisterDto cardRegisterDto,
                          BindingResult bindingResult,
                          @RequestParam(value = "save", required = false) String isSaved,
                          Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("savedCard", cardService.getAllByUserId(orderDto.getUser_id()));
            model.addAttribute("orderForm", orderDto);
            return "order-card";
        }
        if(isSaved != null){
            String[] ownerDate = cardRegisterDto.getOwner().split(" ");
            if (ownerDate.length != 2){
                model.addAttribute("ownerError", "Cardholder data must consist of first and last name");
                return "card-register";
            }

            cardService.addCard(cardRegisterDto, userService.getUserById(orderDto.getUser_id()));
        }
        orderDto.setPaid(true);
        model.addAttribute("card", cardRegisterDto);
        model.addAttribute("address", addressService.getById(orderDto.getAddress_id()));
        return "order-finish";
    }

    @PostMapping("/savedcard")
    public String addCard(@ModelAttribute("orderForm") OrderDto orderDto,
                          @RequestParam(value = "cardId", required = true) long cardId,
                          Model model){
        orderDto.setPaid(true);
        model.addAttribute("card", cardService.getById(cardId));
        model.addAttribute("address", addressService.getById(orderDto.getAddress_id()));
        return "order-finish";
    }

    @GetMapping ("/later")
    public String getLaterPay(@ModelAttribute("orderForm") OrderDto orderDto,
                              Model model){
        orderDto.setPaid(false);
        model.addAttribute("address", addressService.getById(orderDto.getAddress_id()));
        return "order-finish";
}


    @PostMapping("/finish")
    public String addNewOrder(@ModelAttribute("orderForm") OrderDto orderDto,
                              @SessionAttribute("bucket") ArrayList<ProductDto> bucket,
                              SessionStatus status,
                              BindingResult bindingResult,
                              Model model) {
        status.setComplete();
        if(productService.isAvailable(orderDto.getProductDtoList())){
            orderService.addOrder(orderDto);
            bucket.clear();
        }else {
            return "redirect:/bucket";
        }
        return "redirect:/";
    }

}
