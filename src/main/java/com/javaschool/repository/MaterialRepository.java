package com.javaschool.repository;

import com.javaschool.entity.Material;

public interface MaterialRepository {
    Material findByName(String materialName);

    void save(Material material);
}
