package com.javaschool.repository.product;

import com.javaschool.entity.Brand;
import com.javaschool.exception.ProductException;

import java.util.List;

public interface BrandRepository {

    List<Brand> findAll() throws ProductException;

    Brand findById(long id) throws ProductException;

    Brand findByName(String brandName) throws ProductException;

    void save(Brand brand);
}
