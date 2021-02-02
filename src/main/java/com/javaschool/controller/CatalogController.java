package com.javaschool.controller;

import com.javaschool.dto.product.ProductBucketDto;
import com.javaschool.dto.product.ProductDto;
import com.javaschool.dto.product.SelectedParams;
import com.javaschool.exception.ProductException;
import com.javaschool.repository.impl.product.filtration.SearchCriteria;
import com.javaschool.repository.product.BrandRepository;
import com.javaschool.repository.product.CategoryRepository;
import com.javaschool.repository.product.ProductRepository;
import com.javaschool.service.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping("/")
    public String getAllProducts(Model model,
                                 HttpSession session){
        catalogService.getAllProducts(model, session);
        return "products";
    }

    @PostMapping("/")
    public String getProductsByParam(Model model,
                                     @RequestParam(value = "radioCategory", required = false) String categoryName,
                                     @RequestParam(value = "radioBrand", required = false) String brandName,
                                     @RequestParam(value = "radioColor",required = false) String colorName,
                                     @RequestParam(value = "radioMaterial",required = false) String materialName,
                                     @RequestParam(value = "radioSeason",required = false) String seasonName,
                                     @SessionAttribute("params") SelectedParams params){
        catalogService.getProductsByParam(model, categoryName, brandName, colorName, materialName, seasonName, params);
        return "products";
    }


    @GetMapping("/clear-params")
    public String clearParams(HttpSession session){
        catalogService.clearParams(session);
        return "redirect:/";
    }

    @GetMapping("/catalog/product/{id}")
    public String getProduct(@PathVariable("id") long id,
                             Model model){
        catalogService.getProduct(id, model);
        return "product";
    }
}
