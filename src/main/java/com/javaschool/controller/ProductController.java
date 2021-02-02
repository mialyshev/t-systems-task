package com.javaschool.controller;

import com.javaschool.dto.product.*;
import com.javaschool.exception.ProductException;
import com.javaschool.service.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final ColorService colorService;
    private final MaterialService materialService;
    private final SeasonService seasonService;
    private final SizeService sizeService;


    @GetMapping("/category")
    public String getAllCategories(Model model){
        categoryService.getAllCategoriesController(model);
        return "admin-category";
    }

    @PostMapping("/category")
    public String addNewCategory(@ModelAttribute("categoryForm") @Valid CategoryDto categoryDto,
                                 BindingResult bindingResult,
                                 Model model){
        return categoryService.addNewCategoryController(bindingResult, categoryDto, model);
    }

    @GetMapping("/brand")
    public String getAllBrands(Model model){
        brandService.getAllBrandsController(model);
        return "admin-brand";
    }

    @PostMapping("/brand")
    public String addNewBrand(@ModelAttribute("brandForm") @Valid BrandDto brandDto,
                              BindingResult bindingResult,
                              Model model){
        return brandService.addNewBrandController(bindingResult, brandDto, model);
    }

    @GetMapping("/color")
    public String getAllColors(Model model){
        colorService.getAllColorsController(model);
        return "admin-color";
    }

    @PostMapping("/color")
    public String addNewColor(@ModelAttribute("colorForm") @Valid ColorDto colorDto,
                              BindingResult bindingResult,
                              Model model){
        return colorService.addNewColorController(bindingResult, colorDto, model);
    }

    @GetMapping("/material")
    public String getAllMaterials(Model model){
        materialService.getAllMaterialsController(model);
        return "admin-material";
    }

    @PostMapping("/material")
    public String addNewMaterial(@ModelAttribute("materialForm") @Valid MaterialDto materialDto,
                                 BindingResult bindingResult,
                                 Model model){
        return materialService.addNewMaterialController(bindingResult, materialDto, model);
    }

    @GetMapping("/season")
    public String getAllSeasons(Model model){
       seasonService.getAllSeasonsController(model);
        return "admin-season";
    }

    @PostMapping("/season")
    public String addNewSeason(@ModelAttribute("seasonForm") @Valid SeasonDto seasonDto,
                               BindingResult bindingResult,
                               Model model){
        return seasonService.addNewSeasonController(bindingResult, seasonDto, model);
    }

    @GetMapping("/size")
    public String getAllSizes(Model model){
        sizeService.getAllSizesController(model);
        return "admin-size";
    }


    @PostMapping("/size")
    public String addNewSize(@ModelAttribute("sizeForm") @Valid SizeDto sizeDto,
                              BindingResult bindingResult,
                              Model model){
        return sizeService.addNewSizeController(bindingResult, sizeDto, model);
    }

    @GetMapping
    public String addNewProductPage(Model model){
        productService.addNewProductPageController(model);
        return "admin-product-page";
    }

    @PostMapping("/create")
    public String addNewProduct(@ModelAttribute("productForm") @Valid ProductDto productDto,
                                BindingResult bindingResult,
                                @RequestParam("size") float size,
                                Model model) {
        return productService.addNewProductController(productDto, bindingResult, size, model);
    }

    @GetMapping("/add-size-product")
    public String addSizeOrQuantity(Model model){
        model.addAttribute("products", productService.getAll());
        return "admin-add-size-or-quantity-list";
    }

    @GetMapping("/add-size-product/{id}")
    public String addSizeOrQuantityForProduct(@PathVariable("id") long id,
                                    Model model){
        model.addAttribute("productInfo", productService.getById(id));
        return "admin-add-size-or-quantity";
    }

    @PostMapping("/add-size-product/{id}")
    public String addSizeOrQuantityForProduct(@PathVariable("id") long id,
                                              @RequestParam("size") float size,
                                              @RequestParam("quantityProduct") int quantity,
                                              Model model){
       return productService.addSizeOrQuantityForProductController(id, size, quantity, model);
    }
}
