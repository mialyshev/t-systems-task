package com.javaschool.service.order;

import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.dto.order.AddressAdditionDto;
import com.javaschool.dto.order.OrderDto;
import com.javaschool.dto.order.OrderRegisterDto;
import com.javaschool.dto.product.ProductBucketDto;
import com.javaschool.dto.product.ProductDto;
import com.javaschool.dto.product.ProductStatisticDto;
import com.javaschool.dto.user.UserStatisticDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface Order service.
 *
 * @author Marat Ialyshev
 */
public interface OrderService {

    /**
     * Find all list.
     * @return the list
     */
    List<OrderDto> findAll();

    /**
     * Find by id order entity.
     *
     * @param id the id
     * @return the order entity
     */
    OrderDto findById(long id);

    /**
     * Add order entity.
     *
     * @param orderDto the order entity
     */
    void addOrder(OrderRegisterDto orderDto);

    /**
     * Getting a list of possible order statuses.
     *
     */
    List<String> getOrderStatusList();

    /**
     * Getting a list of possible payment statuses.
     *
     */
    List<String> getPaymentStatusList();

    /**
     * Getting a list of possible payment types.
     *
     */
    List<String> getPaymentTypeList();

    /**
     * Update payment type.
     *
     * @param orderId the order id
     *
     */
    void changePayment(long orderId);

    /**
     * Change payment status.
     *
     * @param orderId the order id
     *
     */
    void setPaid(long orderId);

    /**
     * Getting a list of orders by address.
     *
     * @param addressId the address id
     *
     * @return list
     *
     */
    List<OrderDto> getOrdersByAddressId(long addressId);

    /**
     * Update payment status.
     *
     * @param orderId the order id
     * @param paymentStatus new payment status
     *
     */
    void updatePaymentStatus(String paymentStatus, long orderId);

    /**
     * Update order status.
     *
     * @param orderId the order id
     * @param orderStatus new order status
     *
     */
    void updateOrderStatus(String orderStatus, long orderId);

    /**
     * Search for all user orders.
     *
     * @param userId the user id
     * @param isDelivered order status
     *
     * @return list
     *
     */
    List<OrderDto> findAllDeliveredForUser(long userId, boolean isDelivered);

    /**
     * Set products to order.
     *
     * @param orderDto the order entity
     *
     */
    void setOrderProductList(OrderDto orderDto);

    /**
     * Calculation of the order value.
     *
     * @param orderDto the order entity
     *
     * @return the cost
     *
     */
    int getAllPriceForOrder(OrderDto orderDto);

    /**
     * Calculating the cost of orders.
     *
     * @param orderDtoList the list of order entity
     *
     * @return the cost
     *
     */
    int getAllPriceForOrders(List<OrderDto> orderDtoList);

    /**
     * Search for all completed orders.
     *
     * @param isDelivered order status
     *
     * @return list
     *
     */
    List<OrderDto> findAllDelivered(boolean isDelivered);

    /**
     * Search for all orders for the current month.
     *
     * @return list
     *
     */
    List<OrderDto> getMonthOrders();

    /**
     * Search for all orders for the current week.
     *
     * @return list
     *
     */
    List<OrderDto> getWeekOrders();

    /**
     * Getting a list of top products.
     *
     * @return list
     *
     */
    List<ProductStatisticDto> getTopProducts();

    /**
     * Getting a list of top users.
     *
     * @return list
     *
     */
    List<UserStatisticDto> getTopUsers();

    /**
     * Controller for receiving an order form.
     *
     * @param model the model on which we put the required data
     * @param bucket the shopping cart
     * @param orderDto the order entity
     * @param selected list of selected items from the cart
     *
     * @return the title of the html page to display
     *
     */
    String getOrderFormController(Model model, Integer[] selected, ArrayList<ProductBucketDto> bucket, OrderRegisterDto orderDto);

    /**
     * Controller for adding a new ordering address.
     *
     * @param bindingResult bindingResult
     * @param orderDto the order entity
     * @param addressAdditionDto the address entity
     * @param isSaved the option to save the added address
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String addAddressController(BindingResult bindingResult, OrderRegisterDto orderDto, AddressAdditionDto addressAdditionDto, String isSaved, Model model);

    /**
     * Controller for selecting the saved address in the order.
     *
     * @param addressId the address id
     * @param orderDto the order entity
     * @param model the model on which we put the required data
     *
     */
    void addSavedAddressController(OrderRegisterDto orderDto, long addressId, Model model);

    /**
     * Controller for choosing the type of payment.
     *
     * @param orderDto the order entity
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String getPaymentTypeController(OrderRegisterDto orderDto, Model model);

    /**
     * Controller for paying for the order with a new card.
     *
     * @param orderDto the order entity
     * @param bindingResult bindingResult
     * @param cardRegisterDto the card entity
     * @param isSaved the option to save the added card
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String addCardController(BindingResult bindingResult, OrderRegisterDto orderDto, CardRegisterDto cardRegisterDto, String isSaved, Model model);

    /**
     * Controller for selecting the saved card in the order.
     *
     * @param cardId the address id
     * @param orderDto the order entity
     * @param model the model on which we put the required data
     *
     */
    void addSavedCardController(OrderRegisterDto orderDto, long cardId, Model model);

    /**
     * Controller for selecting the option that payment will be made later.
     *
     * @param orderDto the order entity
     * @param model the model on which we put the required data
     *
     */
    void getLaterPayController(OrderRegisterDto orderDto, Model model);

    /**
     * Controller for adding a new order.
     *
     * @param orderDto the order entity
     * @param bucket the shopping cart
     * @param status session status
     *
     */
    void addNewOrderController(OrderRegisterDto orderDto, ArrayList<ProductBucketDto> bucket, SessionStatus status);

    /**
     * Checking the availability of all items in the cart.
     *
     * @param orderDto the order entity
     *
     * @return result of checking
     *
     */
    boolean isAvailable(OrderRegisterDto orderDto);
}
