package com.javaschool.service.impl.product;

import com.javaschool.dto.product.BrandDto;
import com.javaschool.entity.Brand;
import com.javaschool.exception.ProductException;
import com.javaschool.mapper.product.BrandMapperImpl;
import com.javaschool.repository.product.BrandRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BrandServiceImplTest {

    @InjectMocks
    BrandServiceImpl brandService;

    @Mock
    BrandRepository brandRepository;

    @Mock
    BrandMapperImpl brandMapper;

    private Brand getTestBrandEntity(){
        return Brand.builder()
                .id(1)
                .brandName("test")
                .build();
    }

    private BrandDto getTestBrandDto() {
        return BrandDto.builder()
                .brandName("test")
                .build();
    }

    @Test
    public void getAllTest() throws ProductException {
        when(brandRepository.findAll()).thenReturn(Collections.singletonList(getTestBrandEntity()));
        when(brandMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(getTestBrandDto()));
        List<BrandDto> result = brandService.getAll();
        assertEquals(1, result.size());
        BrandDto dto = result.get(0);
        assertEquals(getTestBrandEntity().getBrandName(), dto.getBrandName());
    }

    @Test
    public void getByIdTest() throws ProductException {
        lenient().when(brandRepository.findById(anyInt())).thenReturn(getTestBrandEntity());
        when(brandMapper.toDto(any())).thenReturn(getTestBrandDto());
        BrandDto result = brandService.getById(1);
        assertEquals(getTestBrandEntity().getBrandName(), result.getBrandName());
    }

    @Test
    public void getByNameTest() throws ProductException {
        when(brandRepository.findByName(anyString())).thenReturn(getTestBrandEntity());
        when(brandMapper.toDto(any())).thenReturn(getTestBrandDto());
        BrandDto result = brandService.getByName("Adsa");
        assertEquals(getTestBrandEntity().getBrandName(), result.getBrandName());
    }
}
