package com.javaschool.repository.product;

import com.javaschool.entity.Material;
import com.javaschool.exception.ProductException;

import java.util.List;

/**
 * The interface Material repository.
 *
 * @author Marat Ialyshev
 */
public interface MaterialRepository {

    /**
     * Find all list.
     * @exception ProductException if something going wrong
     * @return the list
     */
    List<Material> findAll() throws ProductException;

    /**
     * Find by id material entity.
     *
     * @param id the id
     * @exception ProductException if material not found
     * @return the material entity
     */
    Material findById(long id) throws ProductException;

    /**
     * Find by name material entity.
     *
     * @param materialName the name of material
     * @exception ProductException if material not found
     * @return the material entity
     */
    Material findByName(String materialName) throws ProductException;

    /**
     * Add material entity.
     *
     * @param material the material entity
     */
    void save(Material material);
}
