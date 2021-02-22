package com.javaschool.service.impl.product;

import com.javaschool.dto.product.CategoryDto;
import com.javaschool.entity.Category;
import com.javaschool.exception.ProductException;
import com.javaschool.mapper.product.CategoryMapperImpl;
import com.javaschool.repository.product.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CategoryMapperImpl categoryMapper;

    @InjectMocks
    CategoryServiceImpl categoryService;

    private Category getTestCategoryEntity() {
        return Category.builder()
                .id(1)
                .categoryName("test")
                .build();
    }

    private CategoryDto getTestCategoryDto() {
        return CategoryDto.builder()
                .categoryName("test")
                .build();
    }


    @Test
    public void getAllTest() throws ProductException {
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(getTestCategoryEntity()));
        when(categoryMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(getTestCategoryDto()));
        List<CategoryDto> result = categoryService.getAll();
        assertEquals(1, result.size());
        CategoryDto dto = result.get(0);
        assertEquals(getTestCategoryEntity().getCategoryName(), dto.getCategoryName());
    }

    @Test
    public void getByIdTest() throws ProductException {
        lenient().when(categoryRepository.findById(anyInt())).thenReturn(getTestCategoryEntity());
        when(categoryMapper.toDto(any())).thenReturn(getTestCategoryDto());
        CategoryDto result = categoryService.getById(1);
        assertEquals(getTestCategoryEntity().getCategoryName(), result.getCategoryName());
    }

    @Test
    public void getByNameTest() throws ProductException {
        when(categoryRepository.findByName(anyString())).thenReturn(getTestCategoryEntity());
        when(categoryMapper.toDto(any())).thenReturn(getTestCategoryDto());
        CategoryDto result = categoryService.getByName("test");
        assertEquals(getTestCategoryEntity().getCategoryName(), result.getCategoryName());
    }

    @Test
    public void addCategoryTest() {
        categoryService.addCategory(getTestCategoryDto());
        verify(categoryRepository, only()).save(any(Category.class));
    }
}
