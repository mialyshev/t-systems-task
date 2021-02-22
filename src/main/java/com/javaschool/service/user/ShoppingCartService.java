package com.javaschool.service.user;

import com.javaschool.dto.product.ProductBucketDto;
import com.javaschool.exception.ProductException;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface Shopping Cart service.
 *
 * @author Marat Ialyshev
 */
public interface ShoppingCartService {

    /**
     * Add product to cart.
     *
     * @param productId the product id
     * @param bucket the shopping cart
     * @param size the size of added item
     * @param quantity the quantity of added item
     */
    void add(long productId, ArrayList<ProductBucketDto> bucket, float size, String quantity);

    /**
     * Delete product from cart.
     *
     * @param productId the product id
     * @param bucket the shopping cart
     */
    void delete(long productId, ArrayList<ProductBucketDto> bucket);

    /**
     * Update cart.
     *
     * @param bucket the shopping cart
     */
    void updateBucket(ArrayList<ProductBucketDto> bucket);

    /**
     * Controller for removing items from the cart.
     *
     * @param bucket the shopping cart
     * @param selectedProducts selected item numbers
     */
    void deleteSelectedProduct(List<ProductBucketDto> bucket, List<ProductBucketDto> selectedProducts);

    /**
     * Controller for displaying the contents of the cart.
     *
     * @param bucket the shopping cart
     * @param model the model on which we put the required data
     */
    String getBucketFormController(Model model, ArrayList<ProductBucketDto> bucket);
}
