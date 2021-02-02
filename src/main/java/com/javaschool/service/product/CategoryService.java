package com.javaschool.service.product;

import com.javaschool.dto.product.CategoryDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAll();

    CategoryDto getById(long id);

    CategoryDto getByName(String categoryName);

    void addCategory(CategoryDto categoryDto);

    void getAllCategoriesController(Model model);

    String addNewCategoryController(BindingResult bindingResult, CategoryDto categoryDto, Model model);
}
