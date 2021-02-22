package com.javaschool.repository.product;

import com.javaschool.entity.Brand;
import com.javaschool.exception.OrderException;
import com.javaschool.exception.ProductException;

import java.util.List;

/**
 * The interface Brand repository.
 *
 * @author Marat Ialyshev
 */
public interface BrandRepository {

    /**
     * Find all list.
     * @exception ProductException if something going wrong
     * @return the list
     */
    List<Brand> findAll() throws ProductException;

    /**
     * Find by id brand entity.
     *
     * @param id the id
     * @exception ProductException if brand not found
     * @return the brand entity
     */
    Brand findById(long id) throws ProductException;

    /**
     * Find by name brand entity.
     *
     * @param brandName the name of brand
     * @exception ProductException if brand not found
     * @return the brand entity
     */
    Brand findByName(String brandName) throws ProductException;

    /**
     * Add brand entity.
     *
     * @param brand the brand entity
     */
    void save(Brand brand);
}
