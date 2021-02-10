package com.javaschool.service.product;

import com.javaschool.dto.product.ColorDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ColorService {

    List<ColorDto> getAll();

    ColorDto getById(long id);

    ColorDto getByName(String colorName);

    void addColor(ColorDto colorDto);

    void getAllColorsController(Model model);

    String addNewColorController(BindingResult bindingResult, ColorDto colorDto, Model model);
}
