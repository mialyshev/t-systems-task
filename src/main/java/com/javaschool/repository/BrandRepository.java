package com.javaschool.repository;

import com.javaschool.entity.Brand;

public interface BrandRepository {

    Brand findByName(String brandName);

    void save(Brand brand);
}
