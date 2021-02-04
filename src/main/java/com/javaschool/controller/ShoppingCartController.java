package com.javaschool.controller;

import com.javaschool.dto.product.ProductBucketDto;
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

    private final ShoppingCartService shoppingCartService;

    @PostMapping("/add/{id}")
    public String addProductToBucket(@SessionAttribute("bucket") ArrayList<ProductBucketDto> bucket,
                                     @RequestParam("size") float size,
                                     @PathVariable("id") long id) {
        shoppingCartService.add(id, bucket, size);
        return "redirect:/catalog/product/" + id;
    }

    @GetMapping()
    public String getBucketForm(Model model,
                                @SessionAttribute("bucket") ArrayList<ProductBucketDto> bucket) {
        return shoppingCartService.getBucketFormController(model, bucket);
    }

    @GetMapping("/delete/{id}")
    public String deleteProductFromBucket(@SessionAttribute("bucket") ArrayList<ProductBucketDto> bucket,
                                          @PathVariable("id") long id) {
        shoppingCartService.delete(id, bucket);
        return "redirect:/bucket";
    }
}
