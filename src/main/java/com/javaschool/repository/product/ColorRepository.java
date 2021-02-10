package com.javaschool.repository.product;

import com.javaschool.entity.Color;
import com.javaschool.exception.ProductException;

import java.util.List;

public interface ColorRepository {

    List<Color> findAll() throws ProductException;

    Color findById(long id) throws ProductException;

    Color findByName(String colorName) throws ProductException;

    void save(Color color);
}
