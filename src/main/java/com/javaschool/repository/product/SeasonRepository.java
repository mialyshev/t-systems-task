package com.javaschool.repository.product;

import com.javaschool.entity.Season;

public interface SeasonRepository {

    Season findByName(String seasonName);

    void save(Season season);
}
