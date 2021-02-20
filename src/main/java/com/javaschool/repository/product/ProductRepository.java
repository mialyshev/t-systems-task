package com.javaschool.repository.product;


import com.javaschool.entity.Product;
import com.javaschool.exception.ProductException;
import com.javaschool.repository.impl.product.filtration.SearchCriteria;

import java.util.List;

/**
 * The interface Product repository.
 *
 * @author Marat Ialyshev
 */
public interface ProductRepository {

    /**
     * Find all list.
     * @exception ProductException if something going wrong
     * @return the list
     */
    List<Product> findAll() throws ProductException;

    /**
     * Find all active products.
     * @exception ProductException if something going wrong
     * @return the list
     */
    List<Product> findAllActive() throws ProductException;

    /**
     * Find all active products by model.
     * @param model the model of product
     * @exception ProductException if something going wrong
     * @return the list
     */
    List<Product> findAllActiveByModel(String model) throws ProductException;

    /**
     * Find by id product entity.
     *
     * @param id the id
     * @exception ProductException if product not found
     * @return the product entity
     */
    Product findById(long id) throws ProductException;

    /**
     * Add product entity.
     *
     * @param product the product entity
     */
    void save(Product product);

    /**
     * Update product entity.
     *
     * @param product the product entity
     */
    void updateProduct(Product product);

    /**
     * Find by params category list.
     *
     * @param params list of params
     * @exception ProductException if category not found
     * @return the list
     */
    List<Product> findByParam(List<SearchCriteria> params) throws ProductException;
}
