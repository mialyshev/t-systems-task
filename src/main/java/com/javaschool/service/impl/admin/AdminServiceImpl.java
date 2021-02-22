package com.javaschool.service.impl.admin;

import com.javaschool.dto.order.OrderDto;
import com.javaschool.service.admin.AdminService;
import com.javaschool.service.order.AddressService;
import com.javaschool.service.order.OrderService;
import com.javaschool.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final OrderService orderService;
    private final UserService userService;
    private final AddressService addressService;

    private static Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public void getAllOrders(Model model) {
        log.info("Get all orders in admin service");
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("users", userService.getAll());
    }

    @Override
    public void getAllNotDeliveredOrders(Model model) {
        log.info("Get all not delivered orders in admin service");
        model.addAttribute("orders", orderService.findAllDelivered(false));
        model.addAttribute("users", userService.getAll());
    }

    @Override
    public void getAllDeliveredOrders(Model model) {
        log.info("Get all delivered orders in admin service");
        model.addAttribute("orders", orderService.findAllDelivered(true));
        model.addAttribute("users", userService.getAll());
    }

    @Override
    public String getOrder(long id, Model model) {
        log.info("Get order with id:" + id + " in admin service");
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
        log.info("Get page for edit payment status for order with id: " + id);
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
        log.info("Get page for edit status for order with id: " + id);
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
        log.info("Get statistics page");
        List<OrderDto> orders = orderService.getMonthOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("revenue", orderService.getAllPriceForOrders(orders));
    }

    @Override
    public void getRevenueForWeekStatisticPage(Model model) {
        log.info("Getting a page of statistics of revenue for a week");
        List<OrderDto> orders = orderService.getWeekOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("revenue", orderService.getAllPriceForOrders(orders));
    }

    @Override
    public void getTopProductsPage(Model model) {
        log.info("Getting a page of statistics of top products");
        model.addAttribute("products", orderService.getTopProducts());
    }

    @Override
    public void getTopUsersPage(Model model) {
        log.info("Getting a page of statistics of top users");
        model.addAttribute("users", orderService.getTopUsers());
    }
}
