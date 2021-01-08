package com.javaschool.repository.product;

import com.javaschool.entity.Color;

import java.util.List;

public interface ColorRepository {

    List<Color> findAll();

    Color findById(long id);

    Color findByName(String colorName);

    void save(Color color);
}
