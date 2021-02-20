package com.javaschool.repository.product;

import com.javaschool.entity.Size;
import com.javaschool.exception.ProductException;

import java.util.List;

/**
 * The interface Size repository.
 *
 * @author Marat Ialyshev
 */
public interface SizeRepository {

    /**
     * Find all list.
     * @exception ProductException if something going wrong
     * @return the list
     */
    List<Size> findAll() throws ProductException;

    /**
     * Find by id size entity.
     *
     * @param id the id
     * @exception ProductException if size not found
     * @return the size entity
     */
    Size findById(long id) throws ProductException;

    /**
     * Find by size size entity.
     *
     * @param size the value of size
     * @exception ProductException if size not found
     * @return the size entity
     */
    Size findBySize(float size) throws ProductException;

    /**
     * Add size entity.
     *
     * @param size the size entity
     */
    void save(Size size);
}
