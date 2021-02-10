package com.javaschool.repository.product;


import com.javaschool.entity.Product;
import com.javaschool.exception.ProductException;
import com.javaschool.repository.impl.product.filtration.SearchCriteria;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll() throws ProductException;

    List<Product> findAllActive() throws ProductException;

    List<Product> findAllActiveByModel(String model) throws ProductException;

    Product findById(long id) throws ProductException;

    void save(Product product);

    List<Product> getProductByOrderId(long orderId);

    void updateProduct(Product product);

    List<Product> findByParam(List<SearchCriteria> params) throws ProductException;
}
