package com.javaschool.repository.product;


import com.javaschool.repository.impl.product.filtration.SearchCriteria;
import com.javaschool.entity.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    List<Product> findAllActive();

    List<Product> findAllActiveByModel(String model);

    Product findById(long id);

    void save(Product product);

    List<Product> getProductByOrderId(long orderId);

    void updateProduct(Product product);

    List<Product> findByParam(List<SearchCriteria> params);
}
