package com.javaschool.repository.order;

import com.javaschool.entity.Order;
import com.javaschool.entity.enums.OrderStatus;

import java.util.List;

public interface OrderRepository {

    List<Order> findAll();

    Order findById(long id);

    void save(Order order);

    void updateOrder(Order order);

    List<Order> findByUserId(long userId);

    List<Order> findByAddressId(long addressId);

    List<Order> findAllDelivered(long userId, OrderStatus orderStatus, boolean isDelivered);

}
