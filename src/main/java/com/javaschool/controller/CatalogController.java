package com.javaschool.controller;

import com.javaschool.dto.product.SelectedParams;
import com.javaschool.service.product.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping("/")
    public String getAllProducts(Model model,
                                 HttpSession session) {
        catalogService.getAllProducts(model, session, 0);
        return "products";
    }

    @PostMapping("/")
    public String getProductsByParam(Model model,
                                     @RequestParam(value = "radioCategory", required = false) String categoryName,
                                     @RequestParam(value = "radioBrand", required = false) String brandName,
                                     @RequestParam(value = "radioColor", required = false) String colorName,
                                     @RequestParam(value = "radioMaterial", required = false) String materialName,
                                     @RequestParam(value = "radioSeason", required = false) String seasonName,
                                     @SessionAttribute("params") SelectedParams params) {
        catalogService.getProductsByParam(model, categoryName, brandName, colorName, materialName, seasonName, params);
        return "products";
    }

    @GetMapping("/{id}")
    public String getAllProductsByPageId(Model model,
                                 @PathVariable("id") int id,
                                 HttpSession session) {
        catalogService.getAllProducts(model, session, id - 1);
        return "products";
    }

    @GetMapping("/clear-params")
    public String clearParams(HttpSession session) {
        catalogService.clearParams(session);
        return "redirect:/";
    }

    @GetMapping("/catalog/product/{id}")
    public String getProduct(@PathVariable("id") long id,
                             Model model) {
        return catalogService.getProduct(id, model);
    }
}
