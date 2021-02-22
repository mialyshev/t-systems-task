package com.javaschool.service.impl.admin;

import com.javaschool.dto.order.OrderDto;
import com.javaschool.service.admin.AdminService;
import com.javaschool.service.order.AddressService;
import com.javaschool.service.order.OrderService;
import com.javaschool.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final OrderService orderService;
    private final UserService userService;
    private final AddressService addressService;

    @Override
    public void getAllOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("users", userService.getAll());
    }

    @Override
    public void getAllNotDeliveredOrders(Model model) {
        model.addAttribute("orders", orderService.findAllDelivered(false));
        model.addAttribute("users", userService.getAll());
    }

    @Override
    public void getAllDeliveredOrders(Model model) {
        model.addAttribute("orders", orderService.findAllDelivered(true));
        model.addAttribute("users", userService.getAll());
    }

    @Override
    public String getOrder(long id, Model model) {
        OrderDto orderDto = orderService.findById(id);
        if(orderDto == null){
            return "404";
        }
        orderService.setOrderProductList(orderDto);
        model.addAttribute("address", addressService.getById(orderDto.getAddress_id()));
        model.addAttribute("email", userService.getById(orderDto.getUser_id()).getEmail());
        model.addAttribute("order", orderDto);
        model.addAttribute("price", orderService.getAllPriceForOrder(orderDto));
        return "admin-order";
    }

    @Override
    public String getPageEditPaymentStatus(long id, Model model) {
        OrderDto orderDto = orderService.findById(id);
        if(orderDto == null){
            return "404";
        }
        model.addAttribute("order", orderDto);
        model.addAttribute("paymentStatuses", orderService.getPaymentStatusList());
        return "admin-order-edit-payment";
    }

    @Override
    public String getPageEditStatus(long id, Model model) {
        OrderDto orderDto = orderService.findById(id);
        if(orderDto == null){
            return "404";
        }
        model.addAttribute("order", orderDto);
        model.addAttribute("orderStatuses", orderService.getOrderStatusList());
        return "admin-order-edit-status";
    }

    @Override
    public void getStatisticPage(Model model) {
        List<OrderDto> orders = orderService.getMonthOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("revenue", orderService.getAllPriceForOrderS(orders));
    }

    @Override
    public void getRevenueForWeekStatisticPage(Model model) {
        List<OrderDto> orders = orderService.getWeekOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("revenue", orderService.getAllPriceForOrderS(orders));
    }

    @Override
    public void getTopProductsPage(Model model) {
        model.addAttribute("products", orderService.getTopProducts());
    }

    @Override
    public void getTopUsersPage(Model model) {
        model.addAttribute("users", orderService.getTopUsers());
    }
}
