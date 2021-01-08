package com.javaschool.mapper.product;

import com.javaschool.dto.product.SeasonDto;
import com.javaschool.entity.Season;
import org.springframework.stereotype.Component;

@Component
public class SeasonMapperImpl {
    public SeasonDto toDto(Season season){
        SeasonDto.SeasonDtoBuilder seasonDto = SeasonDto.builder();

        seasonDto.seasonName(season.getSeasonName());

        return seasonDto.build();
    }
}
