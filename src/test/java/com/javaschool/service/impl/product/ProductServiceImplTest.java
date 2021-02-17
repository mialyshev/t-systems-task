package com.javaschool.service.impl.product;

import com.javaschool.dto.product.*;
import com.javaschool.entity.*;
import com.javaschool.exception.ProductException;
import com.javaschool.mapper.product.ProductMapperImpl;
import com.javaschool.repository.product.*;
import com.javaschool.service.product.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock
    private  ProductRepository productRepository;

    @Mock
    private  ProductMapperImpl productMapper;

    @InjectMocks
    ProductServiceImpl productService;

    private Brand getTestBrandEntity(){
        return Brand.builder()
                .id(1)
                .brandName("test")
                .build();
    }

    private Category getTestCategoryEntity(){
        return Category.builder()
                .id(1)
                .categoryName("test")
                .build();
    }

    private Color getTestColorEntity(){
        return Color.builder()
                .id(1)
                .colorName("test")
                .build();
    }

    private Material getTestMaterialEntity(){
        return Material.builder()
                .id(1)
                .materialName("test")
                .build();
    }

    private Season getTestSeasonEntity(){
        return Season.builder()
                .id(1)
                .seasonName("test")
                .build();
    }

    private Size getTestSizeEntity(){
        return Size.builder()
                .id(1)
                .size(1)
                .build();
    }

    private Product getTestProductEntity(){
        return Product.builder()
                .id(1)
                .quantity(1)
                .price(1)
                .model("test model")
                .active(true)
                .url("test url")
                .category(getTestCategoryEntity())
                .brand(getTestBrandEntity())
                .season(getTestSeasonEntity())
                .material(getTestMaterialEntity())
                .color(getTestColorEntity())
                .size(getTestSizeEntity())
                .build();
    }

    private ProductDto getTestProductDto(){
        return ProductDto.builder()
                .id(1)
                .quantity(1)
                .price(1)
                .model("test model")
                .url("test url")
                .categoryName("test")
                .brandName("test")
                .colorName("test")
                .seasonName("test")
                .materialName("test")
                .size(1)
                .build();
    }

    @Test
    public void getAllActiveTest() throws ProductException {
        List<ProductDto> sameProducts = new ArrayList<>();
        sameProducts.add(getTestProductDto());
        sameProducts.add(getTestProductDto());
        lenient().when(productRepository.findAllActive()).thenReturn(Collections.singletonList(getTestProductEntity()));
        when(productMapper.toDtoList(anyList())).thenReturn(sameProducts);
        List<ProductDto> result = productService.getAll();
        assertEquals(1, result.size());
    }

    @Test
    public void getAllTest() throws ProductException {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(getTestProductEntity()));
        when(productMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(getTestProductDto()));
        List<ProductDto> result = productService.getAll();
        assertEquals(1, result.size());
        ProductDto dto = result.get(0);
        assertEquals(getTestProductDto(), dto);
    }

    @Test
    public void getByIdTest() throws ProductException {
        lenient().when(productRepository.findById(anyInt())).thenReturn(getTestProductEntity());
        when(productMapper.toDto(any())).thenReturn(getTestProductDto());
        ProductDto result = productService.getById(1);
        assertEquals(getTestProductDto(), result);
    }
}
