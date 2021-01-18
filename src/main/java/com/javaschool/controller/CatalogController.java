package com.javaschool.controller;

import com.javaschool.dto.product.ProductDto;
import com.javaschool.repository.impl.product.filtration.SearchCriteria;
import com.javaschool.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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

    @GetMapping("/param")
    public String getProductByParam(){
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        params.add(new SearchCriteria("season_id", ":", "3"));

        List<ProductDto> productDtoList = productService.getProductsByParam(params);
        return "index";
    }
}
