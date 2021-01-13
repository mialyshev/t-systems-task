package com.javaschool.service.product;

import com.javaschool.dto.product.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAll();

    CategoryDto getById(long id);

    CategoryDto getByName(String categoryName);

    void addCategory(CategoryDto categoryDto);
}
