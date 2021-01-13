package com.javaschool.service.product;

import com.javaschool.dto.product.MaterialDto;

import java.util.List;

public interface MaterialService {

    List<MaterialDto> getAll();

    MaterialDto getById(long id);

    MaterialDto getByName(String materialName);

    void addMaterial(MaterialDto materialDto);
}
