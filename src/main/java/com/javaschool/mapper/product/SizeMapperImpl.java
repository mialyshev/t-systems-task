package com.javaschool.mapper.product;

import com.javaschool.dto.product.SeasonDto;
import com.javaschool.dto.product.SizeDto;
import com.javaschool.entity.Season;
import com.javaschool.entity.Size;
import org.springframework.stereotype.Component;

@Component
public class SizeMapperImpl {
    public SizeDto toDto(Size size){
        SizeDto.SizeDtoBuilder sizeDto = SizeDto.builder();

        sizeDto.size(size.getSize());

        return sizeDto.build();
    }
}
