package com.javaschool.service.product;


import com.javaschool.dto.product.ProductBucketDto;
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

    List<ProductBucketDto>getSelectedList(Integer[] selected, ArrayList<ProductBucketDto> bucket);

    boolean isAvailable(List<ProductBucketDto> productDtos);

    int calcPrice(List<ProductBucketDto> productDtos);

    void updateProductQuantity(List<ProductBucketDto> productDtoList);

    List<ProductDto> getProductsByParam(List<SearchCriteria> params);

    void addProductBySizeQuantity(float size, int quantity, long productId);

    List<SizeDto> getAvailableSizesForProduct(long productId);

    void addProductToBucket(long productId, float size, ArrayList<ProductDto> bucket);
}
