package com.javaschool.repository;

import com.javaschool.entity.Season;

public interface SeasonRepository {

    Season findByName(String seasonName);

    void save(Season season);
}
