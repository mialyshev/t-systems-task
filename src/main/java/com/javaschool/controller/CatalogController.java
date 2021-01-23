package com.javaschool.controller;

import com.javaschool.dto.product.ProductBucketDto;
import com.javaschool.dto.product.ProductDto;
import com.javaschool.dto.product.SizeDto;
import com.javaschool.entity.Product;
import com.javaschool.repository.impl.product.filtration.SearchCriteria;
import com.javaschool.repository.product.BrandRepository;
import com.javaschool.repository.product.CategoryRepository;
import com.javaschool.repository.product.ProductRepository;
import com.javaschool.service.product.CategoryService;
import com.javaschool.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/")
    public String getAllProducts(Model model,
                                 HttpSession session){
        if (session.getAttribute("bucket") == null) {
            session.setAttribute("bucket", new ArrayList<ProductBucketDto>());
        }
        List<ProductDto> products = productService.getAllActive();
        model.addAttribute("products", products);
        return "products";
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

    @GetMapping("/catalog/check")
    public String getIndex(){
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        params.add(new SearchCriteria("category", ":", categoryRepository.findById(1)));
        params.add(new SearchCriteria("brand", ":", brandRepository.findById(2)));
        List<Product> products = productRepository.findByParam(params);
        return "redirect:/";
    }
}
