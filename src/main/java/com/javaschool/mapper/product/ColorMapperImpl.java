package com.javaschool.mapper.product;

import com.javaschool.dto.product.ColorDto;
import com.javaschool.entity.Color;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ColorMapperImpl {

    public ColorDto toDto(Color color) {

        if (color == null) {
            return null;
        }
        ColorDto.ColorDtoBuilder colorDto = ColorDto.builder();

        colorDto.colorName(color.getColorName());

        return colorDto.build();
    }

    public List<ColorDto> toDtoList(List<Color> colorList) {
        if (colorList == null) {
            return null;
        }

        List<ColorDto> list = new ArrayList<ColorDto>(colorList.size());
        for (Color color : colorList) {
            list.add(toDto(color));
        }

        return list;
    }
}
