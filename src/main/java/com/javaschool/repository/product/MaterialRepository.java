package com.javaschool.repository.product;

import com.javaschool.entity.Material;
import com.javaschool.exception.ProductException;

import java.util.List;

public interface MaterialRepository {

    List<Material> findAll() throws ProductException;

    Material findById(long id) throws ProductException;

    Material findByName(String materialName) throws ProductException;

    void save(Material material);
}
