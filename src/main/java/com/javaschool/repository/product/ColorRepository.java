package com.javaschool.repository.product;

import com.javaschool.entity.Color;

public interface ColorRepository {

    Color findByName(String colorName);

    void save(Color color);
}
