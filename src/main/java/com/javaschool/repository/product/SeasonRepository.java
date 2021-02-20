package com.javaschool.repository.product;

import com.javaschool.entity.Season;
import com.javaschool.exception.ProductException;

import java.util.List;

/**
 * The interface Season repository.
 *
 * @author Marat Ialyshev
 */
public interface SeasonRepository {

    /**
     * Find all list.
     * @exception ProductException if something going wrong
     * @return the list
     */
    List<Season> findAll() throws ProductException;

    /**
     * Find by id season entity.
     *
     * @param id the id
     * @exception ProductException if season not found
     * @return the season entity
     */
    Season findById(long id) throws ProductException;

    /**
     * Find by name season entity.
     *
     * @param seasonName the name of season
     * @exception ProductException if season not found
     * @return the season entity
     */
    Season findByName(String seasonName) throws ProductException;

    /**
     * Add season entity.
     *
     * @param season the season entity
     */
    void save(Season season);
}
