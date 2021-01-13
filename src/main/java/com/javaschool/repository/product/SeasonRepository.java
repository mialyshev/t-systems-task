package com.javaschool.repository.product;

import com.javaschool.entity.Season;

import java.util.List;

public interface SeasonRepository {

    List<Season> findAll();

    Season findById(long id);

    Season findByName(String seasonName);

    void save(Season season);
}
