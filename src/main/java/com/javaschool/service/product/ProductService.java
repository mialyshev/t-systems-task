package com.javaschool.service.product;


import com.javaschool.dto.product.ProductDto;
import com.javaschool.dto.product.SizeDto;
import com.javaschool.repository.impl.product.filtration.SearchCriteria;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public interface ProductService {

    List<ProductDto> getAll();

    List<ProductDto> getAllActive();

    ProductDto getById(long id);

    void addProduct(ProductDto productDto);

    List<ProductDto>getSelectedList(Integer[] selected);

    boolean isAvailable(List<ProductDto> productDtos);

    void updateBucket(ArrayList<ProductDto> productDtos);

    void updateProductQuantity(List<ProductDto> productDtoList);

    List<ProductDto> getProductsByParam(List<SearchCriteria> params);

    void addProductBySizeQuantity(float size, int quantity, long productId);

    List<SizeDto> getAvailableSizesForProduct(long productId);
}
