package com.javaschool.repository.product;

import com.javaschool.entity.Category;
import com.javaschool.exception.ProductException;

import java.util.List;

public interface CategoryRepository {

    List<Category> findAll() throws ProductException;

    Category findById(long id) throws ProductException;

    Category findByName(String categoryName) throws ProductException;

    void save(Category category);
}
