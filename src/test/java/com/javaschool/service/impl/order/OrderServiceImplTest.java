package com.javaschool.service.impl.order;

import com.javaschool.dto.order.OrderDto;
import com.javaschool.entity.Order;
import com.javaschool.entity.enums.OrderStatus;
import com.javaschool.entity.enums.PaymentStatus;
import com.javaschool.entity.enums.PaymentType;
import com.javaschool.exception.OrderException;
import com.javaschool.mapper.order.OrderMapperImpl;
import com.javaschool.mapper.product.ProductMapperImpl;
import com.javaschool.mapper.user.UserMapperImpl;
import com.javaschool.repository.order.AddressRepository;
import com.javaschool.repository.order.OrderRepository;
import com.javaschool.repository.product.ProductRepository;
import com.javaschool.repository.user.UserRepository;
import com.javaschool.service.order.AddressService;
import com.javaschool.service.product.ProductService;
import com.javaschool.service.user.CardService;
import com.javaschool.service.user.ShoppingCartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapperImpl orderMapper;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductService productService;

    @Mock
    private AddressService addressService;

    @Mock
    private CardService cardService;

    @Mock
    private ShoppingCartService shoppingCartService;

    @Mock
    private ProductMapperImpl productMapper;

    @Mock
    private UserMapperImpl userMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order getOrderEntity() {
        return Order.builder()
                .id(1)
                .orderStatus(OrderStatus.SHIPPED)
                .paymentStatus(PaymentStatus.PAID)
                .paymentType(PaymentType.CARD)
                .dateOfPurchase(LocalDate.of(2000, 1, 1))
                .build();
    }

    private OrderDto getOrderDto() {
        return OrderDto.builder()
                .id(1)
                .user_id(1)
                .address_id(1)
                .orderStatus("test")
                .paymentType("test")
                .paymentStatus("test")
                .paid(true)
                .dateOfPurchase(LocalDate.of(2000, 1, 1))
                .build();
    }

    @Test
    public void findAllTest() throws OrderException {
        lenient().when(orderRepository.findAll()).thenReturn(Collections.singletonList(getOrderEntity()));
        when(orderMapper.toDtoList(any())).thenReturn(Collections.singletonList(getOrderDto()));
        List<OrderDto> result = orderService.findAll();
        assertEquals(1, result.size());
        OrderDto dto = result.get(0);
        assertEquals(getOrderDto(), dto);
    }

    @Test
    public void findByIdTest() throws OrderException {
        lenient().when(orderRepository.findById(anyInt())).thenReturn(getOrderEntity());
        when(orderMapper.toDto(any())).thenReturn(getOrderDto());
        OrderDto result = orderService.findById(1);
        assertEquals(getOrderDto(), result);
    }

}
