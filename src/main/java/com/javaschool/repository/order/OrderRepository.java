package com.javaschool.repository.order;

import com.javaschool.entity.Order;
import com.javaschool.exception.OrderException;
import com.javaschool.exception.UserException;

import java.util.List;

/**
 * The interface Order repository.
 *
 * @author Marat Ialyshev
 */
public interface OrderRepository {

    /**
     * Find all list.
     * @exception OrderException if something going wrong
     * @return the list
     */
    List<Order> findAll() throws OrderException;

    /**
     * Find by id order entity.
     *
     * @param id the id
     * @exception OrderException if order not found
     * @return the order entity
     */
    Order findById(long id) throws OrderException;

    /**
     * Add order entity.
     *
     * @param order the order entity
     */
    void save(Order order);

    /**
     * Update order entity.
     *
     * @param order the order entity
     */
    void updateOrder(Order order);

    /**
     * Search for all user orders.
     *
     * @param userId User id
     * @exception OrderException if something going wrong
     * @return the list
     */
    List<Order> findByUserId(long userId) throws OrderException;

    /**
     * Search for all orders by specified address.
     *
     * @param addressId Address id
     * @exception OrderException if something going wrong
     * @return the list
     */
    List<Order> findByAddressId(long addressId) throws OrderException;

    /**
     * Search for all completed user orders.
     *
     * @param userId User id
     * @param isDelivered Delivery parameter
     * @exception OrderException if something going wrong
     * @return the list
     */
    List<Order> findAllDeliveredByUserId(long userId, boolean isDelivered) throws OrderException;

    /**
     * Search for all completed orders.
     *
     * @param isDelivered Delivery parameter
     * @exception OrderException if something going wrong
     * @return the list
     */
    List<Order> findAllDelivered(boolean isDelivered) throws OrderException;
}
