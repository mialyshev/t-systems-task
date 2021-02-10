package com.javaschool.service.product;

import com.javaschool.dto.product.SizeDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface SizeService {

    List<SizeDto> getAll();

    SizeDto getById(long id);

    SizeDto getByName(float size);

    void addSize(SizeDto sizeDto);

    void getAllSizesController(Model model);

    String addNewSizeController(BindingResult bindingResult, SizeDto sizeDto, Model model);
}
