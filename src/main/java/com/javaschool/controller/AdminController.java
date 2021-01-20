package com.javaschool.controller;

import com.javaschool.dto.order.OrderDto;
import com.javaschool.service.order.OrderService;
import com.javaschool.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    public String getAdminPage(){
        return "admin-page";
    }

    @GetMapping("/orders")
    public String getAllOrders(Model model){
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("users", userService.getAll());
        return "orders-admin";
    }

    @GetMapping("/order/{id}")
    public String editOrderInfo(@PathVariable("id") long id,
                                Model model){
        model.addAttribute("order", orderService.findById(id));
        return "order-edit-admin";
    }

    @GetMapping("/order/edit/pay/{id}")
    public String getPageEditPaymentStatus(@PathVariable("id") long id,
                                  Model model){
        model.addAttribute("order", orderService.findById(id));
        model.addAttribute("paymentStatuses", orderService.getPaymentStatusList());
        return "order-editpayment-admin";
    }

    @PostMapping("/order/edit/pay/{id}")
    public String editPaymentStatus(@PathVariable("id") long id,
                                    @RequestParam(value = "paymentStatus", required = false) String paymentStatus,
                                    Model model){
        orderService.updatePaymentStatus(paymentStatus, id);
        return "redirect:/admin/order/" + id;
    }

    @GetMapping("/order/edit/status/{id}")
    public String getPageEditStatus(@PathVariable("id") long id,
                                           Model model){
        model.addAttribute("order", orderService.findById(id));
        model.addAttribute("orderStatuses", orderService.getOrderStatusList());
        return "order-editstatus-admin";
    }

    @PostMapping("/order/edit/status/{id}")
    public String editStatus(@PathVariable("id") long id,
                                    @RequestParam(value = "orderStatus", required = false) String orderStatus,
                                    Model model){
        orderService.updateOrderStatus(orderStatus, id);
        return "redirect:/admin/order/" + id;
    }
}
