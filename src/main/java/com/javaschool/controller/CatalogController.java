package com.javaschool.controller;

import com.javaschool.dto.product.ProductDto;
import com.javaschool.entity.Product;
import com.javaschool.repository.impl.product.filtration.SearchCriteria;
import com.javaschool.repository.product.BrandRepository;
import com.javaschool.repository.product.CategoryRepository;
import com.javaschool.repository.product.ProductRepository;
import com.javaschool.service.product.CategoryService;
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
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

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

    @GetMapping("/check")
    public String getIndex(){
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        params.add(new SearchCriteria("category", ":", categoryRepository.findById(1)));
        params.add(new SearchCriteria("brand", ":", brandRepository.findById(2)));
        List<Product> products = productRepository.findByParam(params);
        return "redirect:/";
    }
}
