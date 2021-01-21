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

    List<OrderDto> findByUserId(long userId);

    void changePayment(long orderId);

    void setPaid(long orderId);

    List<OrderDto> getOrdersByAddressId(long addressId);

    void updatePaymentStatus(String paymentStatus, long orderId);

    void updateOrderStatus(String orderStatus, long orderId);

    List<OrderDto> findAllDeliveredForUser(long userId, boolean isDelivered);

    void setProductList(OrderDto orderDto);

    int getAllPrice(OrderDto orderDto);

    List<OrderDto> findAllDelivered(boolean isDelivered);
}
