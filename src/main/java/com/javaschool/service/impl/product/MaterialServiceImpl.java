package com.javaschool.service.impl.product;

import com.javaschool.dto.product.MaterialDto;
import com.javaschool.entity.Material;
import com.javaschool.repository.product.MaterialRepository;
import com.javaschool.service.product.MaterialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    @Override
    @Transactional
    public void addMaterial(MaterialDto materialDto) {
        Material material = new Material();
        material.setMaterialName(materialDto.getMaterialName());
        materialRepository.save(material);
    }
}
