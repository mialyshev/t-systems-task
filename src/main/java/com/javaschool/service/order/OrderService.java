package com.javaschool.service.order;

import com.javaschool.dto.order.AddressDto;
import com.javaschool.dto.order.OrderDto;
import com.javaschool.entity.Address;
import com.javaschool.entity.Product;
import com.javaschool.entity.User;

import java.util.List;
import java.util.Set;

public interface OrderService {

    List<OrderDto> findAll();

    OrderDto findById(long id);

    void addOrder(OrderDto orderDto);

    void updateOrderStatus(OrderDto orderDto);

    List<String>getOrderStatusList();

    List<String>getPaymentStatusList();

    List<String>getPaymentTypeList();
}
