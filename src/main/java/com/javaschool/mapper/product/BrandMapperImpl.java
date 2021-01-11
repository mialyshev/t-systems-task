package com.javaschool.mapper.product;

import com.javaschool.dto.product.BrandDto;
import com.javaschool.entity.Brand;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BrandMapperImpl {

    public BrandDto toDto(Brand brand){

        if (brand == null) {
            return null;
        }
        BrandDto.BrandDtoBuilder brandDto = BrandDto.builder();

        brandDto.brandName(brand.getBrandName());

        return brandDto.build();
    }

    public List<BrandDto> toDtoList(List<Brand> brandList) {
        if (brandList == null) {
            return null;
        }

        List<BrandDto> list = new ArrayList<BrandDto>(brandList.size());
        for (Brand brand : brandList) {
            list.add(toDto(brand));
        }

        return list;
    }
}
