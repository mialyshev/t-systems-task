package com.javaschool.controller;

import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.dto.order.AddressAdditionDto;
import com.javaschool.dto.order.OrderRegisterDto;
import com.javaschool.dto.product.ProductBucketDto;
import com.javaschool.service.impl.MessageSender;
import com.javaschool.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.ArrayList;


@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
@SessionAttributes(types = {OrderRegisterDto.class})
public class OrderController {
    private final OrderService orderService;
    private final MessageSender messageSender;

    @GetMapping
    public String getOrderForm(Model model,
                               @RequestParam(value = "selected", required = false) Integer[] selected,
                               @SessionAttribute("bucket") ArrayList<ProductBucketDto> bucket) {
        OrderRegisterDto orderDto = new OrderRegisterDto();
        return orderService.getOrderFormController(model, selected, bucket, orderDto);
    }


    @PostMapping("/address")
    public String addAddress(@ModelAttribute("orderForm") OrderRegisterDto orderDto,
                             @ModelAttribute("addressForm") @Valid AddressAdditionDto addressAdditionDto,
                             BindingResult bindingResult,
                             @RequestParam(value = "save", required = false) String isSaved,
                             Model model) {
        return orderService.addAddressController(bindingResult, orderDto, addressAdditionDto, isSaved, model);
    }


    @GetMapping("/savedaddress/{id}")
    public String addSavedAddress(@ModelAttribute("orderForm") OrderRegisterDto orderDto,
                                  @PathVariable("id") long addressId,
                                  Model model) {
        orderService.addSavedAddressController(orderDto, addressId, model);
        return "order-choose-pay";
    }

    @PostMapping("/payment")
    public String getPaymentType(@ModelAttribute("orderForm") OrderRegisterDto orderDto,
                                 Model model) {
        return orderService.getPaymentTypeController(orderDto, model);
    }

    @PostMapping("/card")
    public String addCard(@ModelAttribute("orderForm") OrderRegisterDto orderDto,
                          @ModelAttribute("cardForm") @Valid CardRegisterDto cardRegisterDto,
                          BindingResult bindingResult,
                          @RequestParam(value = "save", required = false) String isSaved,
                          Model model) {
        return orderService.addCardController(bindingResult, orderDto, cardRegisterDto, isSaved, model);
    }

    @GetMapping("/savedcard/{id}")
    public String addSavedCard(@ModelAttribute("orderForm") OrderRegisterDto orderDto,
                               @PathVariable("id") long cardId,
                               Model model) {
        orderService.addSavedCardController(orderDto, cardId, model);
        return "order-finish";
    }

    @GetMapping("/later")
    public String getLaterPay(@ModelAttribute("orderForm") OrderRegisterDto orderDto,
                              Model model) {
        orderService.getLaterPayController(orderDto, model);
        return "order-finish";
    }


    @PostMapping("/finish")
    public String addNewOrder(@ModelAttribute("orderForm") OrderRegisterDto orderDto,
                              @SessionAttribute("bucket") ArrayList<ProductBucketDto> bucket,
                              SessionStatus status) {
        if(orderService.isAvailable(orderDto)){
            orderService.addNewOrderController(orderDto, bucket, status);
            messageSender.sendMessage();
            return "redirect:/";
        }
        return "redirect:/bucket";
    }

}
