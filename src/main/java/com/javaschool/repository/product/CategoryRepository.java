package com.javaschool.repository.product;

import com.javaschool.entity.Category;

import java.util.List;

public interface CategoryRepository {

    List<Category> findAll();

    Category findById(long id);

    Category findByName(String categoryName);

    void save(Category category);
}
