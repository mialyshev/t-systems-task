package com.javaschool.service.product;

import com.javaschool.dto.product.SelectedParams;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

/**
 * The interface Catalog service.
 *
 * @author Marat Ialyshev
 */
public interface CatalogService {

    /**
     * Find all products.
     */
    void getAllProducts(Model model, HttpSession session);

    /**
     * Find all products by params.
     *
     * @param model the model on which we put the required data
     * @param categoryName param for category
     * @param brandName param for brand
     * @param colorName param for color
     * @param materialName param for material
     * @param seasonName param for season
     * @param params list of params
     */
    void getProductsByParam(Model model, String categoryName, String brandName, String colorName, String materialName, String seasonName, SelectedParams params);

    /**
     * Clear params.
     *
     * @param session http session
     */
    void clearParams(HttpSession session);

    /**
     * Controller for get product entity.
     *
     * @param id the product id
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String getProduct(long id, Model model);
}
