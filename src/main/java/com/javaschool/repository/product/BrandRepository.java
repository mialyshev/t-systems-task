package com.javaschool.repository.product;

import com.javaschool.entity.Brand;

public interface BrandRepository {

    Brand findByName(String brandName);

    void save(Brand brand);
}
