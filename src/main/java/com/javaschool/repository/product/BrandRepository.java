package com.javaschool.repository.product;

import com.javaschool.entity.Brand;

import java.util.List;

public interface BrandRepository {

    List<Brand> findAll();

    Brand findById(long id);

    Brand findByName(String brandName);

    void save(Brand brand);
}
