package com.javaschool.service.product;

import com.javaschool.dto.product.ColorDto;

import java.util.List;

public interface ColorService {

    List<ColorDto> getAll();

    ColorDto getById(long id);

    ColorDto getByName(String colorName);

    void addColor(ColorDto colorDto);
}
