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

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ColorService colorService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final MaterialService materialService;
    private final SeasonService seasonService;

    @GetMapping("/")
    public String getAllProducts(Model model,
                                 HttpSession session) throws ProductException {
        if (session.getAttribute("bucket") == null) {
            session.setAttribute("bucket", new ArrayList<ProductBucketDto>());
        }
        if (session.getAttribute("params") == null) {
            session.setAttribute("params", new SelectedParams());
        }
        SelectedParams selectedParams = (SelectedParams)session.getAttribute("params");
        List<ProductDto> products = productService.getProductsByParamList(selectedParams);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("brands", brandService.getAll());
        model.addAttribute("colors", colorService.getAll());
        model.addAttribute("materials", materialService.getAll());
        model.addAttribute("seasons", seasonService.getAll());
        return "products";
    }

    @PostMapping("/")
    public String getProductsByParam(Model model,
                                     @RequestParam(value = "radioCategory", required = false) String categoryName,
                                     @RequestParam(value = "radioBrand", required = false) String brandName,
                                     @RequestParam(value = "radioColor",required = false) String colorName,
                                     @RequestParam(value = "radioMaterial",required = false) String materialName,
                                     @RequestParam(value = "radioSeason",required = false) String seasonName,
                                     @SessionAttribute("params") SelectedParams params) throws ProductException {
        model.addAttribute("products", productService.getProductsByParam(categoryName, brandName, colorName, materialName, seasonName, params));
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("brands", brandService.getAll());
        model.addAttribute("colors", colorService.getAll());
        model.addAttribute("materials", materialService.getAll());
        model.addAttribute("seasons", seasonService.getAll());
        return "products";
    }


    @GetMapping("/clear-params")
    public String clearParams(HttpSession session){
        SelectedParams selectedParams = (SelectedParams)session.getAttribute("params");
        selectedParams.setCategory(null);
        selectedParams.setBrand(null);
        selectedParams.setColor(null);
        selectedParams.setMaterial(null);
        selectedParams.setSeason(null);
        return "redirect:/";
    }

    @GetMapping("/catalog/product/{id}")
    public String getProduct(@PathVariable("id") long id,
                             Model model){
        model.addAttribute("product", productService.getById(id));
        model.addAttribute("sizes", productService.getAvailableSizesForProduct(id));
        return "product";
    }

    @GetMapping("/catalog/param")
    public String getProductByParam(){
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        params.add(new SearchCriteria("season_id", ":", "3"));

        List<ProductDto> productDtoList = productService.getProductsByParam(params);
        return "index";
    }
    }
