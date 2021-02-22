package com.javaschool.controller;

import com.javaschool.dto.product.ProductStatisticDto;
import com.javaschool.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StatsRestController {

    private final OrderService orderService;

    @GetMapping("/stats")
    public List<ProductStatisticDto> getStats() {
        return orderService.getTopProducts();
    }
}
