package com.javaschool.service.admin;

import org.springframework.ui.Model;

public interface AdminService {

    void getAllOrders(Model model);

    void getAllNotDeliveredOrders(Model model);

    void getAllDeliveredOrders(Model model);

    void getOrder(long id, Model model);

    void getPageEditPaymentStatus(long id, Model model);

    void getPageEditStatus(long id, Model model);

    void getStatisticPage(Model model);

    void getRevenueForWeekStatisticPage(Model model);

    void getTopProductsPage(Model model);

    void getTopUsersPage(Model model);
}
