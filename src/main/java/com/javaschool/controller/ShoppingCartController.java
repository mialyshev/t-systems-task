package com.javaschool.controller;

import com.javaschool.dto.product.ProductBucketDto;
import com.javaschool.dto.product.ProductDto;
import com.javaschool.service.product.ProductService;
import com.javaschool.service.user.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/bucket")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ProductService productService;
    private final ShoppingCartService shoppingCartService;

    @PostMapping("/add/{id}")
    public String addProductToBucket(@SessionAttribute("bucket") ArrayList<ProductBucketDto> bucket,
                                     @RequestParam("size") float size,
                                     @PathVariable("id") long id,
                                     Model model){
        shoppingCartService.add(id, bucket, size);
        return "redirect:/catalog/product/" +id;
    }

    @GetMapping()
    public String getBucketForm(Model model,
                                @SessionAttribute("bucket") ArrayList<ProductBucketDto> bucket){
        if (bucket.isEmpty()){
            model.addAttribute("bucketEmpty", "Your shopping cart is empty. It's time to shop!");
        }
        shoppingCartService.updateBucket(bucket);
        return "bucket";
    }

    @GetMapping("/delete/{id}")
    public String deleteProductFromBucket(@SessionAttribute("bucket") ArrayList<ProductBucketDto> bucket,
                                          @PathVariable("id") long id,
                                          Model model){
        shoppingCartService.delete(id, bucket);
        return "redirect:/bucket";
    }
}
