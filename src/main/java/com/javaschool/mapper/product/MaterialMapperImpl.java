package com.javaschool.mapper.product;

import com.javaschool.dto.product.MaterialDto;
import com.javaschool.entity.Material;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MaterialMapperImpl {
    public MaterialDto toDto(Material material) {

        if (material == null) {
            return null;
        }
        MaterialDto.MaterialDtoBuilder materialDto = MaterialDto.builder();

        materialDto.materialName(material.getMaterialName());

        return materialDto.build();
    }

    public List<MaterialDto> toDtoList(List<Material> materialList) {
        if (materialList == null) {
            return null;
        }

        List<MaterialDto> list = new ArrayList<MaterialDto>(materialList.size());
        for (Material material : materialList) {
            list.add(toDto(material));
        }

        return list;
    }
}
