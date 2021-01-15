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
}
