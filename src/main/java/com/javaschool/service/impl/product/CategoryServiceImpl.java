package com.javaschool.service.impl.product;

import com.javaschool.dto.product.CategoryDto;
import com.javaschool.entity.Category;
import com.javaschool.exception.ProductException;
import com.javaschool.mapper.product.CategoryMapperImpl;
import com.javaschool.repository.product.CategoryRepository;
import com.javaschool.service.product.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapperImpl categoryMapper;

    @Override
    public List<CategoryDto> getAll() {
        List<CategoryDto> categoryDtoList = null;
        try {
            categoryDtoList = categoryMapper.toDtoList(categoryRepository.findAll());
        } catch (ProductException e) {
            log.error("Error getting all the categories", e);
        }catch (Exception e){
            log.error("Error at CategoryService.getAll()", e);
        }
        return categoryDtoList;
    }

    @Override
    public CategoryDto getById(long id) {
        CategoryDto categoryDto = null;
        try {
            categoryDto = categoryMapper.toDto(categoryRepository.findById(id));
        } catch (ProductException e) {
            log.error("Error getting a category by id", e);
        }catch (Exception e){
            log.error("Error at CategoryService.getById()", e);
        }
        return categoryDto;
    }

    @Override
    @Transactional
    public CategoryDto getByName(String categoryName) {
        CategoryDto categoryDto = null;
        try {
            categoryDto = categoryMapper.toDto(categoryRepository.findByName(categoryName));
        } catch (ProductException e) {
            log.error("Error getting a category by name", e);
        }catch (Exception e){
            log.error("Error at CategoryService.getByName()", e);
        }
        return categoryDto;
    }

    @Override
    @Transactional
    public void addCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        categoryRepository.save(category);
    }
}
