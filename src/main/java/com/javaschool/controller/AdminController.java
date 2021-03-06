package com.javaschool.controller;

import com.javaschool.service.admin.AdminService;
import com.javaschool.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final OrderService orderService;
    private final AdminService adminService;

    @GetMapping
    public String getAdminPage() {
        return "admin-page";
    }

    @GetMapping("/orders")
    public String getAllOrders(Model model) {
        adminService.getAllOrders(model);
        return "admin-orders";
    }

    @GetMapping("/orders/not-delivered")
    public String getAllNotDeliveredOrders(Model model) {
        adminService.getAllNotDeliveredOrders(model);
        return "admin-orders-not-delivered";
    }

    @GetMapping("/orders/delivered")
    public String getAllDeliveredOrders(Model model) {
        adminService.getAllDeliveredOrders(model);
        return "admin-orders-delivered";
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable("id") long id,
                           Model model) {
        return adminService.getOrder(id, model);
    }

    @GetMapping("/order/edit/pay/{id}")
    public String getPageEditPaymentStatus(@PathVariable("id") long id,
                                           Model model) {
        return adminService.getPageEditPaymentStatus(id, model);
    }

    @PostMapping("/order/edit/pay/{id}")
    public String editPaymentStatus(@PathVariable("id") long id,
                                    @RequestParam(value = "paymentStatus", required = false) String paymentStatus) {
        orderService.updatePaymentStatus(paymentStatus, id);
        return "redirect:/admin/orders";
    }

    @GetMapping("/order/edit/status/{id}")
    public String getPageEditStatus(@PathVariable("id") long id,
                                    Model model) {
        return adminService.getPageEditStatus(id, model);
    }

    @PostMapping("/order/edit/status/{id}")
    public String editStatus(@PathVariable("id") long id,
                             @RequestParam(value = "orderStatus", required = false) String orderStatus,
                             Model model) {
        orderService.updateOrderStatus(orderStatus, id);
        return "redirect:/admin/orders";
    }

    @GetMapping("/statistic")
    public String getStatisticPage(Model model) {
        adminService.getStatisticPage(model);
        return "admin-statistic";
    }

    @GetMapping("/statistic/week")
    public String getRevenueForWeekStatisticPage(Model model) {
        adminService.getRevenueForWeekStatisticPage(model);
        return "admin-statistic-week";
    }

    @GetMapping("/statistic/products")
    public String getTopProductsPage(Model model) {
        adminService.getTopProductsPage(model);
        return "admin-statistic-products";
    }

    @GetMapping("/statistic/users")
    public String getTopUsersPage(Model model) {
        adminService.getTopUsersPage(model);
        return "admin-statistic-users";
    }
}
