package com.javaschool.mapper.product;

import com.javaschool.dto.product.BrandDto;
import com.javaschool.entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapperImpl {

    public BrandDto toDto(Brand brand){
        BrandDto.BrandDtoBuilder brandDto = BrandDto.builder();

        brandDto.brandName(brand.getBrandName());

        return brandDto.build();
    }
}
