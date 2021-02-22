package com.javaschool.repository.product;

import com.javaschool.entity.Category;
import com.javaschool.exception.ProductException;

import java.util.List;

/**
 * The interface Category repository.
 *
 * @author Marat Ialyshev
 */
public interface CategoryRepository {

    /**
     * Find all list.
     * @exception ProductException if something going wrong
     * @return the list
     */
    List<Category> findAll() throws ProductException;

    /**
     * Find by id category entity.
     *
     * @param id the id
     * @exception ProductException if category not found
     * @return the category entity
     */
    Category findById(long id) throws ProductException;

    /**
     * Find by name category entity.
     *
     * @param categoryName the name of category
     * @exception ProductException if category not found
     * @return the category entity
     */
    Category findByName(String categoryName) throws ProductException;

    /**
     * Add category entity.
     *
     * @param category the category entity
     */
    void save(Category category);
}
