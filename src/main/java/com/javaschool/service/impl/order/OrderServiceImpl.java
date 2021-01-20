package com.javaschool.service.impl.order;

import com.javaschool.dto.order.AddressDto;
import com.javaschool.dto.order.OrderDto;
import com.javaschool.dto.product.ProductDto;
import com.javaschool.entity.Address;
import com.javaschool.entity.Order;
import com.javaschool.entity.Product;
import com.javaschool.entity.User;
import com.javaschool.entity.enums.*;
import com.javaschool.mapper.order.OrderMapperImpl;
import com.javaschool.repository.order.AddressRepository;
import com.javaschool.repository.order.OrderRepository;
import com.javaschool.repository.product.ProductRepository;
import com.javaschool.repository.user.UserRepository;
import com.javaschool.service.order.OrderService;
import com.javaschool.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapperImpl orderMapper;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductService productService;

    @Override
    public List<OrderDto> findAll() {
        List<OrderDto> orderDtoList = null;
        try {
            orderDtoList = orderMapper.toDtoList(orderRepository.findAll());
        } catch (Exception e) {
            log.error("Error getting all orders", e);
        }
        return orderDtoList;
    }

    @Override
    public OrderDto findById(long id) {
        OrderDto orderDto = null;
        try {
            orderDto = orderMapper.toDto(orderRepository.findById(id));
        } catch (Exception e) {
            log.error("Error getting a order by id", e);
        }
        return orderDto;
    }

    @Override
    @Transactional
    public void addOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setUser(userRepository.findById(orderDto.getUser_id()));
        order.setAddress(addressRepository.findById(orderDto.getAddress_id()));
        order.setPaymentType(PaymentType.valueOf(orderDto.getPaymentType()));
        if(orderDto.getPaymentType().equals("CARD")){
            if(orderDto.isPaid()) {
                order.setOrderStatus(OrderStatus.WAITING_FOR_SHIPMENT);
                order.setPaymentStatus(PaymentStatus.PAID);
            }else {
                order.setOrderStatus(OrderStatus.AWAITING_PAYMENT);
                order.setPaymentStatus(PaymentStatus.AWAITING_PAYMENT);
            }
        }
        if(orderDto.getPaymentType().equals("CASH")) {
            order.setOrderStatus(OrderStatus.WAITING_FOR_SHIPMENT);
            order.setPaymentStatus(PaymentStatus.AWAITING_PAYMENT);
        }
        Set<Product> productSet = new HashSet<>();
        for (ProductDto productDto : orderDto.getProductDtoList()){
            productSet.add(productRepository.findById(productDto.getId()));
        }
        order.setProductSet(productSet);
        productService.updateProductQuantity(orderDto.getProductDtoList());
        orderRepository.save(order);
    }

    @Override
    public void updateOrderStatus(OrderDto orderDto) {

    }

    @Override
    public List<String> getOrderStatusList() {
        List<String> strings = new ArrayList<>();
        for(OrderStatus orderStatus : OrderStatus.values()){
            strings.add(orderStatus.name());
        }
        return strings;
    }

    @Override
    public List<String> getPaymentStatusList() {
        List<String> strings = new ArrayList<>();
        for(PaymentStatus paymentStatus : PaymentStatus.values()){
            strings.add(paymentStatus.name());
        }
        return strings;
    }

    @Override
    public List<String> getPaymentTypeList() {
        List<String> strings = new ArrayList<>();
        for(PaymentType paymentType : PaymentType.values()){
            strings.add(paymentType.name());
        }
        return strings;
    }

    @Override
    public List<OrderDto> findByUserId(long userId) {
        List<OrderDto> orderDtoList = null;
        try {
            orderDtoList = orderMapper.toDtoList(orderRepository.findByUserId(userId));
        } catch (Exception e) {
            log.error("Error getting all orders by user id", e);
        }
        return orderDtoList;
    }

    @Override
    @Transactional
    public void changePayment(long orderId) {
        Order order = orderRepository.findById(orderId);
        order.setPaymentType(PaymentType.CASH);
        if (order.getOrderStatus() == OrderStatus.AWAITING_PAYMENT) {
            order.setOrderStatus(OrderStatus.WAITING_FOR_SHIPMENT);
        }
        orderRepository.updateOrder(order);
    }

    @Override
    @Transactional
    public void setPaid(long orderId) {
        Order order = orderRepository.findById(orderId);
        order.setPaymentStatus(PaymentStatus.PAID);
        if (order.getOrderStatus() == OrderStatus.AWAITING_PAYMENT) {
            order.setOrderStatus(OrderStatus.WAITING_FOR_SHIPMENT);
        }
        orderRepository.updateOrder(order);
    }

    @Override
    public List<OrderDto> getOrdersByAddressId(long addressId) {
        List<OrderDto> orderDtoList = null;
        try {
            orderDtoList = orderMapper.toDtoList(orderRepository.findByAddressId(addressId));
        } catch (Exception e) {
            log.error("Error getting all orders by address id", e);
        }
        return orderDtoList;
    }

    @Override
    @Transactional
    public void updatePaymentStatus(String paymentStatus, long orderId) {
        Order order = orderRepository.findById(orderId);
        if(order.getPaymentStatus().toString().equals(paymentStatus)){
            return;
        }
        order.setPaymentStatus(PaymentStatus.valueOf(paymentStatus));
        if(order.getPaymentStatus() == PaymentStatus.PAID & order.getOrderStatus() == OrderStatus.AWAITING_PAYMENT){
            order.setOrderStatus(OrderStatus.WAITING_FOR_SHIPMENT);
        }
        orderRepository.updateOrder(order);
    }

    @Override
    @Transactional
    public void updateOrderStatus(String orderStatus, long orderId) {
        Order order = orderRepository.findById(orderId);
        if(order.getOrderStatus().toString().equals(orderStatus)){
            return;
        }
        order.setOrderStatus(OrderStatus.valueOf(orderStatus));
        orderRepository.updateOrder(order);
    }
}
