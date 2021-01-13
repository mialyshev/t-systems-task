package com.javaschool.service.product;


import com.javaschool.dto.product.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAll();

    List<ProductDto> getAllActive();

    ProductDto getById(long id);

    void addProduct(ProductDto productDto);
}
