package com.javaschool.mapper.product;

import com.javaschool.dto.product.ColorDto;
import com.javaschool.entity.Color;
import org.springframework.stereotype.Component;

@Component
public class ColorMapperImpl {

    public ColorDto toDto(Color color){
        ColorDto.ColorDtoBuilder colorDto = ColorDto.builder();

        colorDto.colorName(color.getColorName());

        return colorDto.build();
    }
}
