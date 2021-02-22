package com.javaschool.service.impl.product;

import com.javaschool.dto.product.CategoryDto;
import com.javaschool.entity.Category;
import com.javaschool.exception.ProductException;
import com.javaschool.mapper.product.CategoryMapperImpl;
import com.javaschool.repository.product.CategoryRepository;
import com.javaschool.service.impl.admin.AdminServiceImpl;
import com.javaschool.service.product.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapperImpl categoryMapper;

    private static Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public List<CategoryDto> getAll() {
        log.info("Get all categories");
        List<CategoryDto> categoryDtoList = null;
        try {
            categoryDtoList = categoryMapper.toDtoList(categoryRepository.findAll());
        } catch (ProductException e) {
            log.error("Error getting all the categories", e);
        } catch (Exception e) {
            log.error("Error at CategoryService.getAll()", e);
        }
        return categoryDtoList;
    }

    @Override
    public CategoryDto getById(long id) {
        log.info("Get category with id: " + id);
        CategoryDto categoryDto = null;
        try {
            categoryDto = categoryMapper.toDto(categoryRepository.findById(id));
        } catch (ProductException e) {
            log.error("Error getting a category by id", e);
        } catch (Exception e) {
            log.error("Error at CategoryService.getById()", e);
        }
        return categoryDto;
    }

    @Override
    @Transactional
    public CategoryDto getByName(String categoryName) {
        log.info("Get category with name: " + categoryName);
        CategoryDto categoryDto = null;
        try {
            categoryDto = categoryMapper.toDto(categoryRepository.findByName(categoryName));
        } catch (ProductException e) {
            log.error("Error getting a category by name", e);
        } catch (Exception e) {
            log.error("Error at CategoryService.getByName()", e);
        }
        return categoryDto;
    }

    @Override
    @Transactional
    public void addCategory(CategoryDto categoryDto) {
        log.info("Save new category");
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        categoryRepository.save(category);
    }

    @Override
    public void getAllCategoriesController(Model model) {
        log.info("Getting all categories to display on the model");
        model.addAttribute("categories", getAll());
        model.addAttribute("categoryForm", new CategoryDto());
    }

    @Override
    @Transactional
    public String addNewCategoryController(BindingResult bindingResult, CategoryDto categoryDto, Model model) {
        log.info("Retrieving information about a new category to save it");
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", getAll());
            return "admin-category";
        }
        if (getByName(categoryDto.getCategoryName()) != null) {
            model.addAttribute("categoryError", "A category with the same name already exists");
            List<CategoryDto> categories = getAll();
            model.addAttribute("categories", categories);
            return "admin-category";
        }
        addCategory(categoryDto);
        return "redirect:/product/category";
    }
}
