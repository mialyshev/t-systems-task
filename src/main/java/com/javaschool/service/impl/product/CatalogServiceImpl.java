package com.javaschool.service.impl.product;

import com.javaschool.dto.product.*;
import com.javaschool.service.product.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final ProductService productService;
    private final ColorService colorService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final MaterialService materialService;
    private final SeasonService seasonService;

    private static Logger log = LoggerFactory.getLogger(CatalogServiceImpl.class);

    @Override
    public void getAllProducts(Model model, HttpSession session, int pageId) {
        log.info("Get all products");
        if (session.getAttribute("bucket") == null) {
            session.setAttribute("bucket", new ArrayList<ProductBucketDto>());
        }
        if (session.getAttribute("params") == null) {
            session.setAttribute("params", new SelectedParams());
        }
        SelectedParams selectedParams = (SelectedParams) session.getAttribute("params");
        List<ProductDto> productsFromRepo = productService.getProductsByParamList(selectedParams);
        int pageSize = 20;
        List<ProductDto> products = new ArrayList<>();
        for(int i = pageSize * pageId; i < pageSize * (pageId + 1); i++){
            if(i == productsFromRepo.size()){
                break;
            }
            products.add(productsFromRepo.get(i));
        }
        List<Integer>pages = getPagesNumber(pageSize, productsFromRepo.size());
        addAttributesToModel(model, products, pages, pageId + 1);
    }

    @Override
    public void getProductsByParam(Model model, String categoryName, String brandName, String colorName, String materialName, String seasonName, SelectedParams params) {
        log.info("Getting all products by parameters to display on the model");
        List<ProductDto> productDtos = productService.getProductsByParam(categoryName, brandName, colorName, materialName, seasonName, params);
        int pageSize = 20;
        List<ProductDto> products = new ArrayList<>();
        List<Integer>pages = getPagesNumber(pageSize, productDtos.size());
        for(int i = 0; i < pageSize; i++){
            if(i == productDtos.size()){
                break;
            }
            products.add(productDtos.get(i));
        }
        addAttributesToModel(model, products, pages, 1);
    }

    private List<Integer> getPagesNumber(int pageSize,
                                              int productListSize){
        List<Integer> pages = new ArrayList<>();
        double pageCount = 0;
        if(productListSize % pageSize == 0){
            pageCount = (productListSize / pageSize) - 1;
        }else {
            pageCount = Math.ceil(productListSize / pageSize);
        }
        for(int i = 1; i <= pageCount + 1; i++){
            pages.add(i);
        }
        return pages;
    }

    private void addAttributesToModel(Model model,
                                      List<ProductDto> productDtoList,
                                      List<Integer> pages,
                                      int currentPage){
        model.addAttribute("products", productDtoList);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("brands", brandService.getAll());
        model.addAttribute("colors", colorService.getAll());
        model.addAttribute("materials", materialService.getAll());
        model.addAttribute("seasons", seasonService.getAll());
        model.addAttribute("pages", pages);
        model.addAttribute("current_page", currentPage);
    }

    @Override
    public void clearParams(HttpSession session) {
        log.info("Clear all parameters");
        SelectedParams selectedParams = (SelectedParams) session.getAttribute("params");
        selectedParams.setCategory(null);
        selectedParams.setBrand(null);
        selectedParams.setColor(null);
        selectedParams.setMaterial(null);
        selectedParams.setSeason(null);
    }

    @Override
    public String getProduct(long id, Model model) {
        log.info("Getting product to display on the model");
        ProductDto productDto = productService.getById(id);
        if(productDto == null){
            return "404";
        }
        model.addAttribute("product", productService.getById(id));
        model.addAttribute("sizes", productService.getAvailableSizesForProduct(id));
        return "product";
    }
}
