package com.javaschool.service.admin;

import org.springframework.ui.Model;

/**
 * The interface Admin service.
 *
 * @author Marat Ialyshev
 */
public interface AdminService {

    /**
     * Controller for receiving all orders.
     *
     * @param model the model on which we put the required data
     */
    void getAllOrders(Model model);

    /**
     * Controller for receiving all not delivered orders.
     *
     * @param model the model on which we put the required data
     */
    void getAllNotDeliveredOrders(Model model);

    /**
     * Controller for receiving all delivered orders.
     *
     * @param model the model on which we put the required data
     */
    void getAllDeliveredOrders(Model model);

    /**
     * Controller for receiving an order by id.
     *
     * @param id the order id
     * @param model the model on which we put the required data
     */
    void getOrder(long id, Model model);

    /**
     * Controller for editing order payment status.
     *
     * @param id the order id
     * @param model the model on which we put the required data
     */
    void getPageEditPaymentStatus(long id, Model model);

    /**
     * Controller for editing order status.
     *
     * @param id the order id
     * @param model the model on which we put the required data
     */
    void getPageEditStatus(long id, Model model);

    /**
     * Controller for getting the statistics page.
     *
     * @param model the model on which we put the required data
     */
    void getStatisticPage(Model model);

    /**
     * Controller for receiving data on revenue for the week.
     *
     * @param model the model on which we put the required data
     */
    void getRevenueForWeekStatisticPage(Model model);

    /**
     * Controller for getting a list of top products.
     *
     * @param model the model on which we put the required data
     */
    void getTopProductsPage(Model model);

    /**
     * Controller for getting a list of top users.
     *
     * @param model the model on which we put the required data
     */
    void getTopUsersPage(Model model);
}
