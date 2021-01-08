package com.javaschool.mapper.product;

import com.javaschool.dto.product.CategoryDto;
import com.javaschool.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl {
    public CategoryDto toDto(Category category){
        CategoryDto.CategoryDtoBuilder categoryDto = CategoryDto.builder();

        categoryDto.categoryName(category.getCategoryName());

        return categoryDto.build();
    }
}
