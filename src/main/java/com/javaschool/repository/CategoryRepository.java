package com.javaschool.repository;

import com.javaschool.entity.Category;

public interface CategoryRepository {
    Category findByName(String categoryName);

    void save(Category category);
}
