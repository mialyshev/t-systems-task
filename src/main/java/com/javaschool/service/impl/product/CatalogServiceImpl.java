package com.javaschool.service.impl.product;

import com.javaschool.dto.product.ProductBucketDto;
import com.javaschool.dto.product.ProductDto;
import com.javaschool.dto.product.SelectedParams;
import com.javaschool.service.product.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final ProductService productService;
    private final ColorService colorService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final MaterialService materialService;
    private final SeasonService seasonService;

    @Override
    public void getAllProducts(Model model, HttpSession session) {
        if (session.getAttribute("bucket") == null) {
            session.setAttribute("bucket", new ArrayList<ProductBucketDto>());
        }
        if (session.getAttribute("params") == null) {
            session.setAttribute("params", new SelectedParams());
        }
        SelectedParams selectedParams = (SelectedParams) session.getAttribute("params");
        List<ProductDto> products = productService.getProductsByParamList(selectedParams);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("brands", brandService.getAll());
        model.addAttribute("colors", colorService.getAll());
        model.addAttribute("materials", materialService.getAll());
        model.addAttribute("seasons", seasonService.getAll());
    }

    @Override
    public void getProductsByParam(Model model, String categoryName, String brandName, String colorName, String materialName, String seasonName, SelectedParams params) {
        model.addAttribute("products", productService.getProductsByParam(categoryName, brandName, colorName, materialName, seasonName, params));
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("brands", brandService.getAll());
        model.addAttribute("colors", colorService.getAll());
        model.addAttribute("materials", materialService.getAll());
        model.addAttribute("seasons", seasonService.getAll());
    }

    @Override
    public void clearParams(HttpSession session) {
        SelectedParams selectedParams = (SelectedParams) session.getAttribute("params");
        selectedParams.setCategory(null);
        selectedParams.setBrand(null);
        selectedParams.setColor(null);
        selectedParams.setMaterial(null);
        selectedParams.setSeason(null);
    }

    @Override
    public String getProduct(long id, Model model) {
        ProductDto productDto = productService.getById(id);
        if(productDto == null){
            return "404";
        }
        model.addAttribute("product", productService.getById(id));
        model.addAttribute("sizes", productService.getAvailableSizesForProduct(id));
        return "product";
    }
}
