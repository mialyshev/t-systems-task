package com.javaschool.controller;

import com.javaschool.dto.product.ProductDto;
import com.javaschool.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;

@Controller
@RequestMapping("/bucket")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ProductService productService;

    @GetMapping("/add/{id}")
    public String addProductToBucket(@SessionAttribute("bucket") ArrayList<ProductDto> bucket,
                                     @PathVariable("id") long id,
                                     Model model){
        bucket.add(productService.getById(id));
        return "redirect:/catalog";
    }

    @GetMapping()
    public String getBucketForm(@SessionAttribute("bucket") ArrayList<ProductDto> bucket){
        productService.updateBucket(bucket);
        return "bucket";
    }

    @GetMapping("/delete/{id}")
    public String deleteProductFromBucket(@SessionAttribute("bucket") ArrayList<ProductDto> bucket,
                                          @PathVariable("id") long id,
                                          Model model){
        bucket.remove(productService.getById(id));
        return "bucket";
    }
}
