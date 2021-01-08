package com.javaschool.service.product;

import com.javaschool.dto.product.SizeDto;

import java.util.List;

public interface SizeService {

    List<SizeDto> getAll();

    SizeDto getById(long id);

    SizeDto getByName(float size);

    void addSize(SizeDto sizeDto);
}
