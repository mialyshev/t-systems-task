package com.javaschool.service.product;


import com.javaschool.dto.product.ProductBucketDto;
import com.javaschool.dto.product.ProductDto;
import com.javaschool.dto.product.SelectedParams;
import com.javaschool.dto.product.SizeDto;
import com.javaschool.exception.ProductException;
import com.javaschool.repository.impl.product.filtration.SearchCriteria;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface Product service.
 *
 * @author Marat Ialyshev
 */
public interface ProductService {

    /**
     * Find all list.
     * @return the list
     */
    List<ProductDto> getAll();

    /**
     * Find by id product entity.
     *
     * @param id the id
     * @return the product entity
     */
    ProductDto getById(long id);

    /**
     * Add product entity.
     *
     * @exception ProductException if something going wrong
     * @param productDto the season entity
     */
    void addProduct(ProductDto productDto) throws ProductException;

    /**
     * Get list of selected products from bucket.
     *
     * @param selected array of selected numbers
     * @param bucket the bucket
     * @return the list
     */
    List<ProductBucketDto> getSelectedList(Integer[] selected, ArrayList<ProductBucketDto> bucket);

    /**
     * Checking the availability of items in the cart.
     *
     * @param productDtos the list of products in bucket
     * @return the result of checking
     */
    boolean isAvailable(List<ProductBucketDto> productDtos);

    /**
     * Cart cost calculation.
     *
     * @param productDtos the list of products in bucket
     * @return the amount
     */
    int calcPrice(List<ProductBucketDto> productDtos);

    /**
     * Updating the number of items in the cart.
     *
     * @param productDtoList the list of products in bucket
     */
    void updateProductQuantity(List<ProductBucketDto> productDtoList);

    /**
     * Adding sizes or quantities for an existing product.
     *
     * @param size the size of product
     * @param quantity the quantity of product
     * @param productId the product id
     */
    void addProductBySizeQuantity(float size, int quantity, long productId);

    /**
     * Getting a list of available sizes for a product.
     *
     * @param productId the product id
     * @return list
     */
    List<SizeDto> getAvailableSizesForProduct(long productId);

    /**
     * Getting a list of products by parameters.
     *
     * @param categoryName the name of category
     * @param brandName the name of brand
     * @param colorName the name of color
     * @param materialName the name of material
     * @param seasonName the name of season
     * @param selectedParams a list that stores the selected parameters
     * @return list
     */
    List<ProductDto> getProductsByParam(String categoryName, String brandName, String colorName, String materialName, String seasonName, SelectedParams selectedParams);

    /**
     * Getting a list of products by parameters.
     *
     * @param selectedParams a list with parameters
     * @return list
     */
    List<ProductDto> getProductsByParamList(SelectedParams selectedParams);

    /**
     * Controller for adding a new product.
     *
     * @param model the model on which we put the required data
     */
    void addNewProductPageController(Model model);

    /**
     * Controller for adding product.
     *
     * @param bindingResult bindingResult
     * @param productDto the product entity
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String addNewProductController(ProductDto productDto, BindingResult bindingResult, float size, Model model);

    /**
     * Controller to get a page for adding size or quantity for an existing item.
     *
     * @param id product id
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String addSizeOrQuantityForProductPageController(long id, Model model);

    /**
     * Controller for adding size or quantity for an existing item.
     *
     * @param id product id
     * @param size the value of size
     * @param quantity the value of quantity
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String addSizeOrQuantityForProductController(long id, float size, int quantity, Model model);
}
