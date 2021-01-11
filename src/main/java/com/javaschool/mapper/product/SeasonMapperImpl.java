package com.javaschool.mapper.product;

import com.javaschool.dto.product.SeasonDto;
import com.javaschool.entity.Season;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeasonMapperImpl {
    public SeasonDto toDto(Season season){

        if (season == null) {
            return null;
        }
        SeasonDto.SeasonDtoBuilder seasonDto = SeasonDto.builder();

        seasonDto.seasonName(season.getSeasonName());

        return seasonDto.build();
    }

    public List<SeasonDto> toDtoList(List<Season> seasonList) {
        if (seasonList == null) {
            return null;
        }

        List<SeasonDto> list = new ArrayList<SeasonDto>(seasonList.size());
        for (Season season : seasonList) {
            list.add(toDto(season));
        }

        return list;
    }
}
