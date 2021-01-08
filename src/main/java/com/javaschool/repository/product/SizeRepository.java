package com.javaschool.repository.product;

import com.javaschool.entity.Size;

public interface SizeRepository {

    Size findBySize(float size);

    void save(Size size);
}
