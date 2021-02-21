package com.javaschool.service.product;

import com.javaschool.dto.product.CategoryDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * The interface Category service.
 *
 * @author Marat Ialyshev
 */
public interface CategoryService {

    /**
     * Find all list.
     * @return the list
     */
    List<CategoryDto> getAll();

    /**
     * Find by id category entity.
     *
     * @param id the id
     * @return the category entity
     */
    CategoryDto getById(long id);

    /**
     * Find by name category entity.
     *
     * @param categoryName the category name
     * @return the category entity
     */
    CategoryDto getByName(String categoryName);

    /**
     * Add category entity.
     *
     * @param categoryDto the category entity
     */
    void addCategory(CategoryDto categoryDto);

    /**
     * Controller for get all categories.
     *
     * @param model the model on which we put the required data
     */
    void getAllCategoriesController(Model model);

    /**
     * Controller for adding category.
     *
     * @param bindingResult bindingResult
     * @param categoryDto the category entity
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String addNewCategoryController(BindingResult bindingResult, CategoryDto categoryDto, Model model);
}
