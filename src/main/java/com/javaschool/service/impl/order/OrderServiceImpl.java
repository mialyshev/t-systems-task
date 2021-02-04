package com.javaschool.service.impl.order;

import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.dto.order.AddressAdditionDto;
import com.javaschool.dto.order.OrderDto;
import com.javaschool.dto.order.OrderRegisterDto;
import com.javaschool.dto.product.ProductBucketDto;
import com.javaschool.dto.product.ProductDto;
import com.javaschool.dto.product.ProductStatisticDto;
import com.javaschool.dto.user.UserStatisticDto;
import com.javaschool.entity.Order;
import com.javaschool.entity.Product;
import com.javaschool.entity.User;
import com.javaschool.entity.enums.OrderStatus;
import com.javaschool.entity.enums.PaymentStatus;
import com.javaschool.entity.enums.PaymentType;
import com.javaschool.exception.OrderException;
import com.javaschool.exception.ProductException;
import com.javaschool.exception.UserException;
import com.javaschool.mapper.order.OrderMapperImpl;
import com.javaschool.mapper.product.ProductMapperImpl;
import com.javaschool.mapper.user.UserMapperImpl;
import com.javaschool.repository.order.AddressRepository;
import com.javaschool.repository.order.OrderRepository;
import com.javaschool.repository.product.ProductRepository;
import com.javaschool.repository.user.UserRepository;
import com.javaschool.service.order.AddressService;
import com.javaschool.service.order.OrderService;
import com.javaschool.service.product.ProductService;
import com.javaschool.service.user.CardService;
import com.javaschool.service.user.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private final AddressService addressService;
    private final CardService cardService;
    private final ShoppingCartService shoppingCartService;
    private final ProductMapperImpl productMapper;
    private final UserMapperImpl userMapper;

    @Override
    public List<OrderDto> findAll() {
        List<OrderDto> orderDtoList = null;
        try {
            orderDtoList = orderMapper.toDtoList(orderRepository.findAll());
        } catch (OrderException e) {
            log.error("Error getting all orders", e);
        } catch (Exception e) {
            log.error("Error at OrderService.findAll()", e);
        }
        return orderDtoList;
    }

    @Override
    public OrderDto findById(long id) {
        OrderDto orderDto = null;
        try {
            orderDto = orderMapper.toDto(orderRepository.findById(id));
        } catch (OrderException e) {
            log.error("Error getting a order by id", e);
        } catch (Exception e) {
            log.error("Error at OrderService.findById()", e);
        }
        return orderDto;
    }

    @Override
    @Transactional
    public void addOrder(OrderRegisterDto orderDto) {
        Order order = new Order();
        try {
            order.setUser(userRepository.findById(orderDto.getUser_id()));
        } catch (UserException e) {
            log.error("Error while set user for order", e);
        }
        try {
            order.setAddress(addressRepository.findById(orderDto.getAddress_id()));
        } catch (UserException e) {
            log.error("Error while ser address for order", e);
        }
        order.setPaymentType(PaymentType.valueOf(orderDto.getPaymentType()));
        if (orderDto.getPaymentType().equals("CARD")) {
            if (orderDto.isPaid()) {
                order.setOrderStatus(OrderStatus.WAITING_FOR_SHIPMENT);
                order.setPaymentStatus(PaymentStatus.PAID);
            } else {
                order.setOrderStatus(OrderStatus.AWAITING_PAYMENT);
                order.setPaymentStatus(PaymentStatus.AWAITING_PAYMENT);
            }
        }
        if (orderDto.getPaymentType().equals("CASH")) {
            order.setOrderStatus(OrderStatus.WAITING_FOR_SHIPMENT);
            order.setPaymentStatus(PaymentStatus.AWAITING_PAYMENT);
        }

        List<Product> productList = new ArrayList<>();
        for (ProductBucketDto productDto : orderDto.getProductDtoList()) {
            for (int i = 0; i < productDto.getQuantityInBucket(); i++) {
                try {
                    productList.add(productRepository.findById(productDto.getProductDto().getId()));
                } catch (ProductException e) {
                    log.error("Error while get product by id for add him to order", e);
                }
            }
        }
        order.setProductList(productList);
        order.setDateOfPurchase(LocalDate.now());
        productService.updateProductQuantity(orderDto.getProductDtoList());
        orderRepository.save(order);
    }

    @Override
    public void updateOrderStatus(OrderDto orderDto) {

    }

    @Override
    public List<String> getOrderStatusList() {
        List<String> strings = new ArrayList<>();
        for (OrderStatus orderStatus : OrderStatus.values()) {
            strings.add(orderStatus.name());
        }
        return strings;
    }

    @Override
    public List<String> getPaymentStatusList() {
        List<String> strings = new ArrayList<>();
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            strings.add(paymentStatus.name());
        }
        return strings;
    }

    @Override
    public List<String> getPaymentTypeList() {
        List<String> strings = new ArrayList<>();
        for (PaymentType paymentType : PaymentType.values()) {
            strings.add(paymentType.name());
        }
        return strings;
    }

    @Override
    public List<OrderDto> findByUserId(long userId) {
        List<OrderDto> orderDtoList = null;
        try {
            orderDtoList = orderMapper.toDtoList(orderRepository.findByUserId(userId));
        } catch (OrderException e) {
            log.error("Error getting all orders by user id", e);
        } catch (Exception e) {
            log.error("Error at OrderService.findByUserId()", e);
        }
        return orderDtoList;
    }

    @Override
    @Transactional
    public void changePayment(long orderId) {
        try {
            Order order = orderRepository.findById(orderId);
            order.setPaymentType(PaymentType.CASH);
            if (order.getOrderStatus() == OrderStatus.AWAITING_PAYMENT) {
                order.setOrderStatus(OrderStatus.WAITING_FOR_SHIPMENT);
            }
            orderRepository.updateOrder(order);
        } catch (OrderException e) {
            log.error("Error updating payment type", e);
        } catch (Exception e) {
            log.error("Error at OrderService.changePayment()", e);
        }
    }

    @Override
    @Transactional
    public void setPaid(long orderId) {
        try {
            Order order = orderRepository.findById(orderId);
            order.setPaymentStatus(PaymentStatus.PAID);
            if (order.getOrderStatus() == OrderStatus.AWAITING_PAYMENT) {
                order.setOrderStatus(OrderStatus.WAITING_FOR_SHIPMENT);
            }
            orderRepository.updateOrder(order);
        } catch (OrderException e) {
            log.error("Error updating payment status", e);
        } catch (Exception e) {
            log.error("Error at OrderService.setPaid()", e);
        }
    }

    @Override
    public List<OrderDto> getOrdersByAddressId(long addressId) {
        List<OrderDto> orderDtoList = null;
        try {
            orderDtoList = orderMapper.toDtoList(orderRepository.findByAddressId(addressId));
        } catch (OrderException e) {
            log.error("Error getting all orders by address id", e);
        } catch (Exception e) {
            log.error("Error at OrderService.getOrdersByAddressId()", e);
        }
        return orderDtoList;
    }

    @Override
    @Transactional
    public void updatePaymentStatus(String paymentStatus, long orderId) {
        try {
            Order order = orderRepository.findById(orderId);
            if (order.getPaymentStatus().toString().equals(paymentStatus)) {
                return;
            }
            order.setPaymentStatus(PaymentStatus.valueOf(paymentStatus));
            if (order.getPaymentStatus() == PaymentStatus.PAID & order.getOrderStatus() == OrderStatus.AWAITING_PAYMENT) {
                order.setOrderStatus(OrderStatus.WAITING_FOR_SHIPMENT);
            }
            orderRepository.updateOrder(order);
        } catch (OrderException e) {
            log.error("Error updating payment status", e);
        } catch (Exception e) {
            log.error("Error at OrderService.updatePaymentStatus()", e);
        }
    }

    @Override
    @Transactional
    public void updateOrderStatus(String orderStatus, long orderId) {
        try {
            Order order = orderRepository.findById(orderId);
            if (order.getOrderStatus().toString().equals(orderStatus)) {
                return;
            }
            order.setOrderStatus(OrderStatus.valueOf(orderStatus));
            orderRepository.updateOrder(order);
        } catch (OrderException e) {
            log.error("Error updating order status", e);
        } catch (Exception e) {
            log.error("Error at OrderService.updateOrderStatus()", e);
        }
    }

    @Override
    public List<OrderDto> findAllDeliveredForUser(long userId, boolean isDelivered) {
        List<OrderDto> orderDtoList = null;
        try {
            if (isDelivered) {
                orderDtoList = orderMapper.toDtoList(orderRepository.findAllDeliveredByUserId(userId, true));
            } else {
                orderDtoList = orderMapper.toDtoList(orderRepository.findAllDeliveredByUserId(userId, false));
            }
        } catch (OrderException e) {
            log.error("Error getting all delivered orders by user id", e);
        } catch (Exception e) {
            log.error("Error at OrderService.findAllDeliveredForUser()", e);
        }
        return orderDtoList;
    }

    @Override
    @Transactional
    public void setOrderProductList(OrderDto orderDto) {
        try {
            Order order = orderRepository.findById(orderDto.getId());
            List<Product> products = new ArrayList<>(order.getProductList());
            orderDto.setProductDtoList(productMapper.toDtoList(products));
        } catch (OrderException e) {
            log.error("Error getting all products for order", e);
        } catch (Exception e) {
            log.error("Error at OrderService.setOrderProductList()", e);
        }
    }

    @Override
    public int getAllPriceForOrder(OrderDto orderDto) {
        int price = 0;
        for (ProductDto productDto : orderDto.getProductDtoList()) {
            price += productDto.getPrice();
        }
        return price;
    }


    @Override
    public int getAllPriceForOrderS(List<OrderDto> orderDtoList) {
        int fullPrice = 0;
        for (OrderDto orderDto : orderDtoList) {
            if (orderDto.getProductDtoList() == null) {
                setOrderProductList(orderDto);
            }
            fullPrice += getAllPriceForOrder(orderDto);
        }
        return fullPrice;
    }

    @Override
    public List<OrderDto> findAllDelivered(boolean isDelivered) {
        List<OrderDto> orderDtoList = null;
        try {
            if (isDelivered) {
                orderDtoList = orderMapper.toDtoList(orderRepository.findAllDelivered(true));
            } else {
                orderDtoList = orderMapper.toDtoList(orderRepository.findAllDelivered(false));
            }
        } catch (OrderException e) {
            log.error("Error getting all delivered orders", e);
        } catch (Exception e) {
            log.error("Error at OrderService.findAllDelivered()", e);
        }
        return orderDtoList;
    }

    @Override
    @Transactional
    public List<OrderDto> getMonthOrders() {
        List<OrderDto> orderDtoListFromBd = null;
        List<OrderDto> orderDtoList = new ArrayList<>();
        try {
            orderDtoListFromBd = orderMapper.toDtoList(orderRepository.findAll());
        } catch (OrderException e) {
            log.error("Error getting all orders for current month", e);
        } catch (Exception e) {
            log.error("Error at OrderService.getMonthOrders()", e);
        }
        int currentMonth = LocalDate.now().getMonthValue();
        for (OrderDto orderDto : orderDtoListFromBd) {
            if (orderDto.getDateOfPurchase().getMonthValue() == currentMonth) {
                setOrderProductList(orderDto);
                orderDtoList.add(orderDto);
            }
        }
        return orderDtoList;
    }

    @Override
    @Transactional
    public List<OrderDto> getWeekOrders() {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = LocalDate.now().get(weekFields.weekOfWeekBasedYear());
        List<OrderDto> orderDtoListFromBd = null;
        List<OrderDto> orderDtoList = new ArrayList<>();
        try {
            orderDtoListFromBd = orderMapper.toDtoList(orderRepository.findAll());
        } catch (OrderException e) {
            log.error("Error getting all orders for current week", e);
        } catch (Exception e) {
            log.error("Error at OrderService.getWeekOrders()", e);
        }
        for (OrderDto orderDto : orderDtoListFromBd) {
            if (orderDto.getDateOfPurchase().get(weekFields.weekOfWeekBasedYear()) == weekNumber) {
                setOrderProductList(orderDto);
                orderDtoList.add(orderDto);
            }
        }
        return orderDtoList;
    }

    @Override
    @Transactional
    public List<ProductDto> getTopProducts() {
        List<OrderDto> orderDtoListFromBd = null;
        List<ProductDto> productDtoList = new ArrayList<>();
        try {
            orderDtoListFromBd = orderMapper.toDtoList(orderRepository.findAll());
        } catch (OrderException e) {
            log.error("Error getting top products", e);
        } catch (Exception e) {
            log.error("Error at OrderService.getTopProducts()", e);
        }
        List<ProductStatisticDto> productStatistic = new ArrayList<>();
        boolean isEquals;
        for (OrderDto orderDto : orderDtoListFromBd) {
            setOrderProductList(orderDto);
            for (ProductDto productDto : orderDto.getProductDtoList()) {
                isEquals = false;
                for (ProductStatisticDto productStatisticDto : productStatistic) {
                    if (productStatisticDto.getProductDto().equals(productDto)) {
                        productStatisticDto.setCount(productStatisticDto.getCount() + 1);
                        isEquals = true;
                        break;
                    }
                }
                if (!isEquals) {
                    ProductStatisticDto productStatisticDto = new ProductStatisticDto();
                    productStatisticDto.setProductDto(productDto);
                    productStatisticDto.setCount(1);
                    productStatistic.add(productStatisticDto);
                }
            }
        }
        while (productDtoList.size() != 10 & !productStatistic.isEmpty()) {
            int max = 0;
            ProductStatisticDto productStatisticDtoMax = null;
            for (ProductStatisticDto productStatisticDto : productStatistic) {
                if (productStatisticDto.getCount() > max) {
                    max = productStatisticDto.getCount();
                    productStatisticDtoMax = productStatisticDto;
                }
            }
            productDtoList.add(productStatisticDtoMax.getProductDto());
            productStatistic.remove(productStatisticDtoMax);
        }
        return productDtoList;
    }

    @Override
    @Transactional
    public List<UserStatisticDto> getTopUsers() {
        List<User> users = null;
        try {
            users = userRepository.findAll();
        } catch (UserException e) {
            log.error("Error while getting all users", e);
        }
        List<UserStatisticDto> userStatisticDtos = new ArrayList<>();
        for (User user : users) {
            UserStatisticDto userStatisticDto = new UserStatisticDto();
            userStatisticDto.setUserDto(userMapper.toDto(user));
            userStatisticDto.setPriceForOrders(getAllPriceForOrderS(orderMapper.toDtoList(user.getOrders())));
            userStatisticDtos.add(userStatisticDto);
        }
        while (userStatisticDtos.size() > 10) {
            int min = Integer.MAX_VALUE;
            UserStatisticDto userStatisticDtoMin = null;
            for (UserStatisticDto userStatisticDto : userStatisticDtos) {
                if (userStatisticDto.getPriceForOrders() < min) {
                    min = userStatisticDto.getPriceForOrders();
                    userStatisticDtoMin = userStatisticDto;
                }
            }
            userStatisticDtos.remove(userStatisticDtoMin);
        }
        return userStatisticDtos;
    }

    @Override
    public String getOrderFormController(Model model, Integer[] selected, ArrayList<ProductBucketDto> bucket, OrderRegisterDto orderDto) {
        if (selected == null) {
            model.addAttribute("orderError", "Please select at least one product");
            return "bucket";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userFromBd = null;
        try {
            userFromBd = userRepository.findByEmail(authentication.getName());
        } catch (UserException e) {
            log.error("Error getting user for complete order", e);
        }
        orderDto.setProductDtoList(productService.getSelectedList(selected, bucket));
        orderDto.setUser_id(userFromBd.getId());
        model.addAttribute("savedAddress", addressService.getAllSaved(orderDto.getUser_id()));
        model.addAttribute("addressForm", new AddressAdditionDto());
        model.addAttribute("orderForm", orderDto);
        return "order-address";
    }

    @Override
    public String addAddressController(BindingResult bindingResult, OrderRegisterDto orderDto, AddressAdditionDto addressAdditionDto, String isSaved, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("savedAddress", addressService.getAllSaved(orderDto.getUser_id()));
            return "order-address";
        }
        if (isSaved != null) {
            addressAdditionDto.setSaved(true);
        } else {
            addressAdditionDto.setSaved(false);
        }
        try {
            addressService.addAddress(addressAdditionDto, userRepository.findById(orderDto.getUser_id()));
        } catch (UserException e) {
            log.error("Error while saved address from order processing", e);
        }
        orderDto.setAddress_id(addressService.getLastByUserId(orderDto.getUser_id()).getId());
        model.addAttribute("paymentType", getPaymentTypeList());
        return "order-choose-pay";
    }

    @Override
    public void addSavedAddressController(OrderRegisterDto orderDto, long addressId, Model model) {
        orderDto.setAddress_id(addressId);
        model.addAttribute("paymentType", getPaymentTypeList());
    }

    @Override
    public String getPaymentTypeController(OrderRegisterDto orderDto, Model model) {
        if (orderDto.getPaymentType().equals("CARD")) {
            model.addAttribute("savedCard", cardService.getAllByUserId(orderDto.getUser_id()));
            model.addAttribute("cardForm", new CardRegisterDto());
            return "order-card";
        }
        orderDto.setPaid(false);
        model.addAttribute("address", addressService.getById(orderDto.getAddress_id()));
        return "order-finish";
    }

    @Override
    public String addCardController(BindingResult bindingResult, OrderRegisterDto orderDto, CardRegisterDto cardRegisterDto, String isSaved, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("savedCard", cardService.getAllByUserId(orderDto.getUser_id()));
            return "order-card";
        }
        if (isSaved != null) {
            String[] ownerDate = cardRegisterDto.getOwner().split(" ");
            if (ownerDate.length != 2) {
                model.addAttribute("ownerError", "Cardholder data must consist of first and last name");
                return "card-register";
            }

            try {
                cardService.addCard(cardRegisterDto, userRepository.findById(orderDto.getUser_id()));
            } catch (UserException e) {
                log.error("Error add new card for user while order processing", e);
            }
        }
        orderDto.setPaid(true);
        model.addAttribute("card", cardRegisterDto);
        model.addAttribute("address", addressService.getById(orderDto.getAddress_id()));
        model.addAttribute("orderPrice", productService.calcPrice(orderDto.getProductDtoList()));
        return "order-finish";
    }

    @Override
    public void addSavedCardController(OrderRegisterDto orderDto, long cardId, Model model) {
        orderDto.setPaid(true);
        model.addAttribute("card", cardService.getById(cardId));
        model.addAttribute("address", addressService.getById(orderDto.getAddress_id()));
        model.addAttribute("orderPrice", productService.calcPrice(orderDto.getProductDtoList()));
    }

    @Override
    public void getLaterPayController(OrderRegisterDto orderDto, Model model) {
        orderDto.setPaid(false);
        model.addAttribute("address", addressService.getById(orderDto.getAddress_id()));
        model.addAttribute("orderPrice", productService.calcPrice(orderDto.getProductDtoList()));
    }

    @Override
    @Transactional
    public String addNewOrderController(OrderRegisterDto orderDto, ArrayList<ProductBucketDto> bucket, SessionStatus status) {
        status.setComplete();
        if (productService.isAvailable(orderDto.getProductDtoList())) {
            addOrder(orderDto);
            shoppingCartService.deleteSelectedProduct(bucket, orderDto.getProductDtoList());
        } else {
            return "redirect:/bucket";
        }
        return "redirect:/";
    }
}
