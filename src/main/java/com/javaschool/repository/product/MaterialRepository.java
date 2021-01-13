package com.javaschool.repository.product;

import com.javaschool.entity.Material;

import java.util.List;

public interface MaterialRepository {

    List<Material> findAll();

    Material findById(long id);

    Material findByName(String materialName);

    void save(Material material);
}
