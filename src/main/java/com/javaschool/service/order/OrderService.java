package com.javaschool.service.order;

import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.dto.order.AddressAdditionDto;
import com.javaschool.dto.order.AddressDto;
import com.javaschool.dto.order.OrderDto;
import com.javaschool.dto.order.OrderRegisterDto;
import com.javaschool.dto.product.ProductBucketDto;
import com.javaschool.dto.product.ProductDto;
import com.javaschool.dto.user.UserDto;
import com.javaschool.dto.user.UserStatisticDto;
import com.javaschool.entity.Address;
import com.javaschool.entity.Product;
import com.javaschool.entity.User;
import com.javaschool.exception.ProductException;
import com.javaschool.exception.UserException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface OrderService {

    List<OrderDto> findAll();

    OrderDto findById(long id);

    void addOrder(OrderRegisterDto orderDto);

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

    void setOrderProductList(OrderDto orderDto);

    int getAllPriceForOrder(OrderDto orderDto);

    int getAllPriceForOrderS(List<OrderDto> orderDtoList);

    List<OrderDto> findAllDelivered(boolean isDelivered);

    List<OrderDto> getMonthOrders();

    List<OrderDto> getWeekOrders();

    List<ProductDto> getTopProducts();

    List<UserStatisticDto> getTopUsers();

    String getOrderFormController(Model model, Integer[] selected, ArrayList<ProductBucketDto> bucket, OrderRegisterDto orderDto);

    String addAddressController(BindingResult bindingResult, OrderRegisterDto orderDto, AddressAdditionDto addressAdditionDto, String isSaved, Model model);

    void addSavedAddressController(OrderRegisterDto orderDto, long addressId, Model model);

    String getPaymentTypeController(OrderRegisterDto orderDto, Model model);

    String addCardController(BindingResult bindingResult, OrderRegisterDto orderDto, CardRegisterDto cardRegisterDto, String isSaved, Model model);

    void addSavedCardController(OrderRegisterDto orderDto, long cardId, Model model);

    void getLaterPayController(OrderRegisterDto orderDto, Model model);

    String addNewOrderController(OrderRegisterDto orderDto, ArrayList<ProductBucketDto> bucket, SessionStatus status);
}
