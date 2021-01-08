package com.javaschool.repository.product;

import com.javaschool.entity.Size;

import java.util.List;

public interface SizeRepository {

    List<Size> findAll();

    Size findById(long id);

    Size findBySize(float size);

    void save(Size size);
}
