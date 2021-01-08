package com.javaschool.mapper.product;

import com.javaschool.dto.product.MaterialDto;
import com.javaschool.entity.Material;
import org.springframework.stereotype.Component;

@Component
public class MaterialMapperImpl {
    public MaterialDto toDto(Material material){
        MaterialDto.MaterialDtoBuilder materialDto = MaterialDto.builder();

        materialDto.materialName(material.getMaterialName());

        return materialDto.build();
    }
}
