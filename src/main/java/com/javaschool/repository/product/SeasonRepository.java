package com.javaschool.repository.product;

import com.javaschool.entity.Season;
import com.javaschool.exception.ProductException;

import java.util.List;

public interface SeasonRepository {

    List<Season> findAll() throws ProductException;

    Season findById(long id) throws ProductException;

    Season findByName(String seasonName) throws ProductException;

    void save(Season season);
}
