package com.javaschool.repository.product;

import com.javaschool.entity.Color;
import com.javaschool.exception.ProductException;

import java.util.List;

/**
 * The interface Color repository.
 *
 * @author Marat Ialyshev
 */
public interface ColorRepository {

    /**
     * Find all list.
     * @exception ProductException if something going wrong
     * @return the list
     */
    List<Color> findAll() throws ProductException;

    /**
     * Find by id color entity.
     *
     * @param id the id
     * @exception ProductException if color not found
     * @return the color entity
     */
    Color findById(long id) throws ProductException;

    /**
     * Find by name color entity.
     *
     * @param colorName the name of color
     * @exception ProductException if color not found
     * @return the color entity
     */
    Color findByName(String colorName) throws ProductException;

    /**
     * Add color entity.
     *
     * @param color the color entity
     */
    void save(Color color);
}
