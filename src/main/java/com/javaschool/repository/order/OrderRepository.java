package com.javaschool.repository.order;

import com.javaschool.entity.Order;
import com.javaschool.exception.OrderException;

import java.util.List;

public interface OrderRepository {

    List<Order> findAll() throws OrderException;

    Order findById(long id) throws OrderException;

    void save(Order order);

    void updateOrder(Order order);

    List<Order> findByUserId(long userId) throws OrderException;

    List<Order> findByAddressId(long addressId) throws OrderException;

    List<Order> findAllDeliveredByUserId(long userId, boolean isDelivered) throws OrderException;

    List<Order> findAllDelivered(boolean isDelivered) throws OrderException;
}
