package com.javaschool.service.product;

import com.javaschool.dto.product.BrandDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface BrandService {

    List<BrandDto> getAll();

    BrandDto getById(long id);

    BrandDto getByName(String brandName);

    void addBrand(BrandDto brandDto);

    void getAllBrandsController(Model model);

    String addNewBrandController(BindingResult bindingResult, BrandDto brandDto, Model model);
}
