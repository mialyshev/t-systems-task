package com.javaschool.controller;

import com.javaschool.dto.order.OrderDto;
import com.javaschool.dto.product.ProductDto;
import com.javaschool.service.order.AddressService;
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
    private final AddressService addressService;

    @GetMapping
    public String getAdminPage(){
        return "admin-page";
    }

    @GetMapping("/orders")
    public String getAllOrders(Model model){
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("users", userService.getAll());
        return "admin-orders";
    }

    @GetMapping("/orders/not-delivered")
    public String getAllNotDeliveredOrders(Model model){
        model.addAttribute("orders", orderService.findAllDelivered(false));
        model.addAttribute("users", userService.getAll());
        return "admin-orders-not-delivered";
    }

    @GetMapping("/orders/delivered")
    public String getAllDeliveredOrders(Model model){
        model.addAttribute("orders", orderService.findAllDelivered(true));
        model.addAttribute("users", userService.getAll());
        return "admin-orders-delivered";
    }

    @GetMapping("/order/{id}")
    public String editOrderInfo(@PathVariable("id") long id,
                                Model model){
        OrderDto orderDto = orderService.findById(id);
        orderService.setOrderProductList(orderDto);
        model.addAttribute("address", addressService.getById(orderDto.getAddress_id()));
        model.addAttribute("email", userService.getById(orderDto.getUser_id()).getEmail());
        model.addAttribute("order", orderDto);
        model.addAttribute("price", orderService.getAllPriceForOrder(orderDto));
        return "admin-order";
    }

    @GetMapping("/order/edit/pay/{id}")
    public String getPageEditPaymentStatus(@PathVariable("id") long id,
                                  Model model){
        model.addAttribute("order", orderService.findById(id));
        model.addAttribute("paymentStatuses", orderService.getPaymentStatusList());
        return "admin-order-edit-payment";
    }

    @PostMapping("/order/edit/pay/{id}")
    public String editPaymentStatus(@PathVariable("id") long id,
                                    @RequestParam(value = "paymentStatus", required = false) String paymentStatus,
                                    Model model){
        orderService.updatePaymentStatus(paymentStatus, id);
        return "redirect:/admin/orders";
    }

    @GetMapping("/order/edit/status/{id}")
    public String getPageEditStatus(@PathVariable("id") long id,
                                           Model model){
        model.addAttribute("order", orderService.findById(id));
        model.addAttribute("orderStatuses", orderService.getOrderStatusList());
        return "admin-order-edit-status";
    }

    @PostMapping("/order/edit/status/{id}")
    public String editStatus(@PathVariable("id") long id,
                                    @RequestParam(value = "orderStatus", required = false) String orderStatus,
                                    Model model){
        orderService.updateOrderStatus(orderStatus, id);
        return "redirect:/admin/orders";
    }

    @GetMapping("/statistic")
    public String getStatisticPage(Model model){
        List<OrderDto> orders = orderService.getMonthOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("revenue", orderService.getAllPriceForOrderS(orders));
        return "admin-statistic";
    }

    @GetMapping("/statistic/week")
    public String getRevenueForWeekStatisticPage(Model model){
        List<OrderDto> orders = orderService.getWeekOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("revenue", orderService.getAllPriceForOrderS(orders));
        return "admin-statistic-week";
    }

    @GetMapping("/statistic/products")
    public String getTopProductsPage(Model model){
        model.addAttribute("products", orderService.getTopProducts());
        return "admin-statistic-products";
    }

    @GetMapping("/statistic/users")
    public String getTopUsersPage(Model model){
        model.addAttribute("users", orderService.getTopUsers());
        return "admin-statistic-users";
    }
}
