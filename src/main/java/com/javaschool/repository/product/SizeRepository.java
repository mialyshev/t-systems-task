package com.javaschool.repository.product;

import com.javaschool.entity.Size;
import com.javaschool.exception.ProductException;

import java.util.List;

public interface SizeRepository {

    List<Size> findAll() throws ProductException;

    Size findById(long id) throws ProductException;

    Size findBySize(float size) throws ProductException;

    void save(Size size);
}
