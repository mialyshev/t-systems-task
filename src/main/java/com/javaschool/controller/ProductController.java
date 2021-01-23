package com.javaschool.controller;

import com.javaschool.dto.product.*;
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
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("categoryForm", new CategoryDto());
        return "admin-category";
    }

    @PostMapping("/category")
    public String addNewCategory(@ModelAttribute("categoryForm") @Valid CategoryDto categoryDto,
                                 BindingResult bindingResult,
                                 Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "admin-category";
        }
        if(categoryService.getByName(categoryDto.getCategoryName()) != null){
            model.addAttribute("categoryError", "A category with the same name already exists");
            List<CategoryDto> categories = categoryService.getAll();
            model.addAttribute("categories", categories);
            return "admin-category";
        }
        categoryService.addCategory(categoryDto);
        return "redirect:/product/category";
    }

    @GetMapping("/brand")
    public String getAllBrands(Model model){
        model.addAttribute("brands", brandService.getAll());
        model.addAttribute("brandForm", new BrandDto());
        return "admin-brand";
    }

    @PostMapping("/brand")
    public String addNewBrand(@ModelAttribute("brandForm") @Valid BrandDto brandDto,
                              BindingResult bindingResult,
                              Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("brands", brandService.getAll());
            return "admin-brand";
        }
        if(brandService.getByName(brandDto.getBrandName()) != null){
            model.addAttribute("brandError", "A brand with the same name already exists");
            List<BrandDto> brands = brandService.getAll();
            model.addAttribute("brands", brands);
            return "admin-brand";
        }
        brandService.addBrand(brandDto);
        return "redirect:/product/brand";
    }

    @GetMapping("/color")
    public String getAllColors(Model model){
        model.addAttribute("colors", colorService.getAll());
        model.addAttribute("colorForm", new ColorDto());
        return "admin-color";
    }

    @PostMapping("/color")
    public String addNewColor(@ModelAttribute("colorForm") @Valid ColorDto colorDto,
                              BindingResult bindingResult,
                              Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("colors", colorService.getAll());
            return "admin-color";
        }
        if(colorService.getByName(colorDto.getColorName()) != null){
            model.addAttribute("colorError", "A color with the same name already exists");
            List<ColorDto> colors = colorService.getAll();
            model.addAttribute("colors", colors);
            return "admin-color";
        }
        colorService.addColor(colorDto);
        return "redirect:/product/color";
    }

    @GetMapping("/material")
    public String getAllMaterials(Model model){
        model.addAttribute("materials", materialService.getAll());
        model.addAttribute("materialForm", new MaterialDto());
        return "admin-material";
    }

    @PostMapping("/material")
    public String addNewMaterial(@ModelAttribute("materialForm") @Valid MaterialDto materialDto,
                                 BindingResult bindingResult,
                                 Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("materials", materialService.getAll());
            return "admin-material";
        }
        if(materialService.getByName(materialDto.getMaterialName()) != null){
            model.addAttribute("materialError", "A material with the same name already exists");
            List<MaterialDto> materials = materialService.getAll();
            model.addAttribute("materials", materials);
            return "admin-material";
        }
        materialService.addMaterial(materialDto);
        return "redirect:/product/material";
    }

    @GetMapping("/season")
    public String getAllSeasons(Model model){
        model.addAttribute("seasons", seasonService.getAll());
        model.addAttribute("seasonForm", new SeasonDto());
        return "admin-season";
    }

    @PostMapping("/season")
    public String addNewSeason(@ModelAttribute("seasonForm") @Valid SeasonDto seasonDto,
                               BindingResult bindingResult,
                               Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("seasons", seasonService.getAll());
            return "admin-season";
        }
        if(seasonService.getByName(seasonDto.getSeasonName()) != null){
            model.addAttribute("seasonError", "A season with the same name already exists");
            List<SeasonDto> seasons = seasonService.getAll();
            model.addAttribute("seasons", seasons);
            return "admin-season";
        }
        seasonService.addSeason(seasonDto);
        return "redirect:/product/season";
    }

    @GetMapping("/size")
    public String getAllSizes(Model model){
        model.addAttribute("sizes", sizeService.getAll());
        model.addAttribute("sizeForm", new SizeDto());
        return "admin-size";
    }


    @PostMapping("/size")
    public String addNewSize(@ModelAttribute("sizeForm") @Valid SizeDto sizeDto,
                              BindingResult bindingResult,
                              Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("sizes", sizeService.getAll());
            return "admin-size";
        }
        if(sizeService.getByName(sizeDto.getSize()) != null){
            model.addAttribute("sizeError", "A size with the same value already exists");
            List<SizeDto> sizes = sizeService.getAll();
            model.addAttribute("sizes", sizes);
            return "admin-size";
        }
        sizeService.addSize(sizeDto);
        return "redirect:/product/size";
    }

    @GetMapping
    public String addNewProduct(Model model){
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("brands", brandService.getAll());
        model.addAttribute("colors", colorService.getAll());
        model.addAttribute("materials", materialService.getAll());
        model.addAttribute("seasons", seasonService.getAll());
        model.addAttribute("productForm", new ProductDto());
        return "admin-product-page";
    }

    @PostMapping("/create")
    public String addNewProduct(@ModelAttribute("productForm") @Valid ProductDto productDto,
                                BindingResult bindingResult,
                                @RequestParam("size") float size,
                                Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("categories", categoryService.getAll());
            model.addAttribute("brands", brandService.getAll());
            model.addAttribute("colors", colorService.getAll());
            model.addAttribute("materials", materialService.getAll());
            model.addAttribute("seasons", seasonService.getAll());
            return "admin-product-page";
        }
        productDto.setSize(size);
        productService.addProduct(productDto);
        return "redirect:/product";
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
                                              @RequestParam("quantity") int quantity,
                                              Model model){
        if(quantity <= 0){
            model.addAttribute("quantityError", "Quantity must be greater than 0");
            model.addAttribute("productInfo", productService.getById(id));
            return "admin-add-size-or-quantity";
        }
        productService.addProductBySizeQuantity(size, quantity, id);
        return "redirect:/product/add-size-product/" + id;
    }
}
