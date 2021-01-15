package com.javaschool.controller;

import com.javaschool.dto.product.ProductDto;
import com.javaschool.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final ProductService productService;

    @GetMapping
    public String getAllProducts(Model model){
        List<ProductDto> products = productService.getAllActive();
        model.addAttribute("products", products);
        return "product";
    }
}
