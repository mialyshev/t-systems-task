package com.javaschool.repository.order;

import com.javaschool.entity.Order;

import java.util.List;

public interface OrderRepository {

    List<Order> findAll();

    Order findById(long id);

    void save(Order order);

    void updateOrder(Order order);

    List<Order> findByUserId(long userId);

    List<Order> findByAddressId(long addressId);
}
