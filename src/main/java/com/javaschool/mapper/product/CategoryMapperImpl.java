package com.javaschool.mapper.product;

import com.javaschool.dto.product.CategoryDto;
import com.javaschool.entity.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapperImpl {
    public CategoryDto toDto(Category category){
        CategoryDto.CategoryDtoBuilder categoryDto = CategoryDto.builder();

        categoryDto.categoryName(category.getCategoryName());

        return categoryDto.build();
    }

    public List<CategoryDto> toDtoList(List<Category> categoryList) {
        if (categoryList == null) {
            return null;
        }

        List<CategoryDto> list = new ArrayList<CategoryDto>(categoryList.size());
        for (Category category : categoryList) {
            list.add(toDto(category));
        }

        return list;
    }
}
