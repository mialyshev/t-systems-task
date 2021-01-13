package com.javaschool.service.product;

import com.javaschool.dto.product.BrandDto;

import java.util.List;

public interface BrandService {

    List<BrandDto> getAll();

    BrandDto getById(long id);

    BrandDto getByName(String brandName);

    void addBrand(BrandDto brandDto);
}
