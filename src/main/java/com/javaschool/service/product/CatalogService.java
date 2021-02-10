package com.javaschool.service.product;

import com.javaschool.dto.product.SelectedParams;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public interface CatalogService {

    void getAllProducts(Model model, HttpSession session);

    void getProductsByParam(Model model, String categoryName, String brandName, String colorName, String materialName, String seasonName, SelectedParams params);

    void clearParams(HttpSession session);

    void getProduct(long id, Model model);
}
