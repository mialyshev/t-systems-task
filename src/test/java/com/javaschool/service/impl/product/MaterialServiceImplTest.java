package com.javaschool.service.impl.product;

import com.javaschool.dto.product.MaterialDto;
import com.javaschool.entity.Material;
import com.javaschool.exception.ProductException;
import com.javaschool.mapper.product.MaterialMapperImpl;
import com.javaschool.repository.product.MaterialRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MaterialServiceImplTest {

    @Mock
    MaterialRepository materialRepository;

    @Mock
    MaterialMapperImpl materialMapper;

    @InjectMocks
    MaterialServiceImpl materialService;

    private Material getTestMaterialEntity() {
        return Material.builder()
                .id(1)
                .materialName("test")
                .build();
    }

    private MaterialDto getTestMaterialDto() {
        return MaterialDto.builder()
                .materialName("test")
                .build();
    }


    @Test
    public void getAllTest() throws ProductException {
        when(materialRepository.findAll()).thenReturn(Collections.singletonList(getTestMaterialEntity()));
        when(materialMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(getTestMaterialDto()));
        List<MaterialDto> result = materialService.getAll();
        assertEquals(1, result.size());
        MaterialDto dto = result.get(0);
        assertEquals(getTestMaterialEntity().getMaterialName(), dto.getMaterialName());
    }

    @Test
    public void getByIdTest() throws ProductException {
        lenient().when(materialRepository.findById(anyInt())).thenReturn(getTestMaterialEntity());
        when(materialMapper.toDto(any())).thenReturn(getTestMaterialDto());
        MaterialDto result = materialService.getById(1);
        assertEquals(getTestMaterialEntity().getMaterialName(), result.getMaterialName());
    }

    @Test
    public void getByNameTest() throws ProductException {
        when(materialRepository.findByName(anyString())).thenReturn(getTestMaterialEntity());
        when(materialMapper.toDto(any())).thenReturn(getTestMaterialDto());
        MaterialDto result = materialService.getByName("test");
        assertEquals(getTestMaterialEntity().getMaterialName(), result.getMaterialName());
    }

    @Test
    public void addMaterialTest() {
        materialService.addMaterial(getTestMaterialDto());
        verify(materialRepository, only()).save(any(Material.class));
    }

}
