package com.javaschool.mapper.product;

import com.javaschool.dto.product.ProductDto;
import com.javaschool.entity.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapperImpl {

    public ProductDto toDto(Product product){
        ProductDto.ProductDtoBuilder productDto = ProductDto.builder();

        productDto.id(product.getId());
        productDto.quantity(product.getQuantity());
        productDto.price(product.getPrice());
        productDto.model(product.getModel());
        productDto.url(product.getUrl());
        productDto.categoryName(product.getCategory().getCategoryName());
        productDto.brandName(product.getBrand().getBrandName());
        productDto.seasonName(product.getSeason().getSeasonName());
        productDto.colorName(product.getColor().getColorName());
        productDto.materialName(product.getMaterial().getMaterialName());
        productDto.size(product.getSize().getSize());

        return productDto.build();
    }

    public List<ProductDto> toDtoList(List<Product> productList) {
        if (productList == null) {
            return null;
        }

        List<ProductDto> productDtoList = new ArrayList<ProductDto>(productList.size());
        for (Product product : productList) {
            productDtoList.add(toDto(product));
        }

        return productDtoList;
    }
}
