package com.javaschool.service.product;


import com.javaschool.dto.product.ProductBucketDto;
import com.javaschool.dto.product.ProductDto;
import com.javaschool.dto.product.SelectedParams;
import com.javaschool.dto.product.SizeDto;
import com.javaschool.exception.ProductException;
import com.javaschool.repository.impl.product.filtration.SearchCriteria;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

public interface ProductService {

    List<ProductDto> getAll();

    List<ProductDto> getAllActive();

    ProductDto getById(long id);

    void addProduct(ProductDto productDto) throws ProductException;

    List<ProductBucketDto>getSelectedList(Integer[] selected, ArrayList<ProductBucketDto> bucket);

    boolean isAvailable(List<ProductBucketDto> productDtos);

    int calcPrice(List<ProductBucketDto> productDtos);

    void updateProductQuantity(List<ProductBucketDto> productDtoList);

    List<ProductDto> getProductsByParam(List<SearchCriteria> params);

    void addProductBySizeQuantity(float size, int quantity, long productId);

    List<SizeDto> getAvailableSizesForProduct(long productId);

    void addProductToBucket(long productId, float size, ArrayList<ProductDto> bucket);

    List<ProductDto> getProductsByParam(String categoryName, String brandName, String colorName, String materialName, String seasonName, SelectedParams selectedParams);

    List<ProductDto> getProductsByParamList(SelectedParams selectedParams);

    void addNewProductPageController(Model model);

    String addNewProductController(ProductDto productDto, BindingResult bindingResult, float size, Model model);

    String addSizeOrQuantityForProductController(long id, float size, int quantity, Model model);
}
