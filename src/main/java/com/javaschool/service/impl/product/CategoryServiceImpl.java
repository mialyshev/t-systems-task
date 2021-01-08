package com.javaschool.service.impl.product;

import com.javaschool.dto.product.CategoryDto;
import com.javaschool.entity.Category;
import com.javaschool.repository.product.CategoryRepository;
import com.javaschool.service.product.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void addCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        categoryRepository.save(category);
    }
}
