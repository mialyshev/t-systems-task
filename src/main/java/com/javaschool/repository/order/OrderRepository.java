package com.javaschool.repository.order;

import com.javaschool.entity.Order;

import java.util.List;

public interface OrderRepository {

    List<Order> findAll();

    Order findById(long id);

    void save(Order order);

    void updateOrderStatus(Order order);
}
