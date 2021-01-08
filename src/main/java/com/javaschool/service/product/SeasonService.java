package com.javaschool.service.product;

import com.javaschool.dto.product.SeasonDto;

import java.util.List;

public interface SeasonService {

    List<SeasonDto> getAll();

    SeasonDto getById(long id);

    SeasonDto getByName(String seasonName);

    void addSeason(SeasonDto seasonDto);
}
