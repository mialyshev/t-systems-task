package com.javaschool.controller;

import com.javaschool.dto.order.OrderDto;
import com.javaschool.dto.product.*;
import com.javaschool.service.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
@SessionAttributes(types = {ProductDto.class})
public class ProductController {
    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final ColorService colorService;
    private final MaterialService materialService;
    private final SeasonService seasonService;
    private final SizeService sizeService;

    @GetMapping
    public String getAdminProductPage(Model model){
        return "product-admin-page";
    }

    @GetMapping("/category")
    public String getAllCategories(Model model){
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("categoryForm", new CategoryDto());
        return "category";
    }

    @PostMapping("/category")
    public String addNewCategory(@ModelAttribute("categoryForm") @Valid CategoryDto categoryDto,
                                 BindingResult bindingResult,
                                 HttpSession httpSession,
                                 Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "category";
        }
        if(categoryService.getByName(categoryDto.getCategoryName()) != null){
            model.addAttribute("categoryError", "A category with the same name already exists");
            List<CategoryDto> categories = categoryService.getAll();
            model.addAttribute("categories", categories);
            return "category";
        }
        categoryService.addCategory(categoryDto);
        if(httpSession.getAttribute("productForm") != null){
            return "redirect:/product/create";
        }
        return "redirect:/product/category";
    }

    @GetMapping("/brand")
    public String getAllBrands(Model model){
        model.addAttribute("brands", brandService.getAll());
        model.addAttribute("brandForm", new BrandDto());
        return "brand";
    }

    @PostMapping("/brand")
    public String addNewBrand(@ModelAttribute("brandForm") @Valid BrandDto brandDto,
                              BindingResult bindingResult,
                              HttpSession httpSession,
                              Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("brands", brandService.getAll());
            return "brand";
        }
        if(brandService.getByName(brandDto.getBrandName()) != null){
            model.addAttribute("brandError", "A brand with the same name already exists");
            List<BrandDto> brands = brandService.getAll();
            model.addAttribute("brands", brands);
            return "brand";
        }
        brandService.addBrand(brandDto);
        if(httpSession.getAttribute("productForm") != null){
            return "redirect:/product/create";
        }
        return "redirect:/product/brand";
    }

    @GetMapping("/color")
    public String getAllColors(Model model){
        model.addAttribute("colors", colorService.getAll());
        model.addAttribute("colorForm", new ColorDto());
        return "color";
    }

    @PostMapping("/color")
    public String addNewColor(@ModelAttribute("colorForm") @Valid ColorDto colorDto,
                              BindingResult bindingResult,
                              HttpSession httpSession,
                              Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("colors", colorService.getAll());
            return "color";
        }
        if(colorService.getByName(colorDto.getColorName()) != null){
            model.addAttribute("colorError", "A color with the same name already exists");
            List<ColorDto> colors = colorService.getAll();
            model.addAttribute("colors", colors);
            return "color";
        }
        colorService.addColor(colorDto);
        if(httpSession.getAttribute("productForm") != null){
            return "redirect:/product/create";
        }
        return "redirect:/product/color";
    }

    @GetMapping("/material")
    public String getAllMaterials(Model model){
        model.addAttribute("materials", materialService.getAll());
        model.addAttribute("materialForm", new MaterialDto());
        return "material";
    }

    @PostMapping("/material")
    public String addNewMaterial(@ModelAttribute("materialForm") @Valid MaterialDto materialDto,
                                 BindingResult bindingResult,
                                 HttpSession httpSession,
                                 Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("materials", materialService.getAll());
            return "material";
        }
        if(materialService.getByName(materialDto.getMaterialName()) != null){
            model.addAttribute("materialError", "A material with the same name already exists");
            List<MaterialDto> materials = materialService.getAll();
            model.addAttribute("materials", materials);
            return "material";
        }
        materialService.addMaterial(materialDto);
        if(httpSession.getAttribute("productForm") != null){
            return "redirect:/product/create";
        }
        return "redirect:/product/material";
    }

    @GetMapping("/season")
    public String getAllSeasons(Model model){
        model.addAttribute("seasons", seasonService.getAll());
        model.addAttribute("seasonForm", new SeasonDto());
        return "season";
    }

    @PostMapping("/season")
    public String addNewSeason(@ModelAttribute("seasonForm") @Valid SeasonDto seasonDto,
                               BindingResult bindingResult,
                               HttpSession httpSession,
                               Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("seasons", seasonService.getAll());
            return "season";
        }
        if(seasonService.getByName(seasonDto.getSeasonName()) != null){
            model.addAttribute("seasonError", "A season with the same name already exists");
            List<SeasonDto> seasons = seasonService.getAll();
            model.addAttribute("seasons", seasons);
            return "season";
        }
        seasonService.addSeason(seasonDto);
        if(httpSession.getAttribute("productForm") != null){
            return "redirect:/product/create";
        }
        return "redirect:/product/season";
    }

    @GetMapping("/size")
    public String getAllSizes(Model model){
        model.addAttribute("sizes", sizeService.getAll());
        model.addAttribute("sizeForm", new SizeDto());
        return "size";
    }


    @PostMapping("/size")
    public String addNewBrand(@ModelAttribute("sizeForm") @Valid SizeDto sizeDto,
                              BindingResult bindingResult,
                              HttpSession httpSession,
                              Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("sizes", sizeService.getAll());
            return "size";
        }
        if(sizeService.getByName(sizeDto.getSize()) != null){
            model.addAttribute("sizeError", "A size with the same value already exists");
            List<SizeDto> sizes = sizeService.getAll();
            model.addAttribute("sizes", sizes);
            return "size";
        }
        sizeService.addSize(sizeDto);
        if(httpSession.getAttribute("productForm") != null){
            return "redirect:/product/create";
        }
        return "redirect:/product/size";
    }

    @GetMapping("/create")
    public String addNewProduct(HttpSession httpSession,
                                Model model){
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("brands", brandService.getAll());
        model.addAttribute("colors", colorService.getAll());
        model.addAttribute("materials", materialService.getAll());
        model.addAttribute("seasons", seasonService.getAll());

        if(httpSession.getAttribute("productForm") == null){
            model.addAttribute("productForm", new ProductDto());
        }
        return "create-product";
    }

    @PostMapping("/create")
    public String addNewProduct(@ModelAttribute("productForm") @Valid ProductDto productDto,
                                BindingResult bindingResult,
                                @RequestParam("size") float size,
                                SessionStatus status,
                                Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("categories", categoryService.getAll());
            model.addAttribute("brands", brandService.getAll());
            model.addAttribute("colors", colorService.getAll());
            model.addAttribute("materials", materialService.getAll());
            model.addAttribute("seasons", seasonService.getAll());
            return "create-product";
        }
        status.setComplete();
        productDto.setSize(size);
        productService.addProduct(productDto);
        return "redirect:/catalog";
    }
}
