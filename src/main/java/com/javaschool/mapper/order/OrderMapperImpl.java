package com.javaschool.mapper.order;

import com.javaschool.dto.order.OrderDto;
import com.javaschool.entity.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapperImpl {

    public OrderDto toDto(Order order) {
        if (order == null) {
            return null;
        }
        OrderDto.OrderDtoBuilder orderDto = OrderDto.builder();
        orderDto.id(order.getId());
        orderDto.user_id(order.getUser().getId());
        orderDto.address_id(order.getAddress().getId());
        orderDto.orderStatus(order.getOrderStatus().name());
        orderDto.paymentStatus(order.getPaymentStatus().name());
        orderDto.paymentType(order.getPaymentType().name());
        orderDto.dateOfPurchase(order.getDateOfPurchase());
        if(order.getPaymentStatus().toString().equals("PAID")){
            orderDto.paid(true);
        }else {
            orderDto.paid(false);
        }
        return orderDto.build();
    }

    public List<OrderDto> toDtoList(List<Order> orderList) {
        if (orderList == null) {
            return null;
        }

        List<OrderDto> list = new ArrayList<OrderDto>(orderList.size());
        for (Order order : orderList) {
            list.add(toDto(order));
        }

        return list;
    }
}
