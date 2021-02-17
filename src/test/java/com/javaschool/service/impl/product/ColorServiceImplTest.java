package com.javaschool.service.impl.product;

import com.javaschool.dto.product.ColorDto;
import com.javaschool.entity.Color;
import com.javaschool.exception.ProductException;
import com.javaschool.mapper.product.ColorMapperImpl;
import com.javaschool.repository.product.ColorRepository;
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
public class ColorServiceImplTest {

    @Mock
    ColorRepository colorRepository;

    @Mock
    ColorMapperImpl colorMapper;

    @InjectMocks
    ColorServiceImpl colorService;

    private Color getTestColorEntity(){
        return Color.builder()
                .id(1)
                .colorName("test")
                .build();
    }

    private ColorDto getTestColorDto() {
        return ColorDto.builder()
                .colorName("test")
                .build();
    }


    @Test
    public void getAllTest() throws ProductException {
        when(colorRepository.findAll()).thenReturn(Collections.singletonList(getTestColorEntity()));
        when(colorMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(getTestColorDto()));
        List<ColorDto> result = colorService.getAll();
        assertEquals(1, result.size());
        ColorDto dto = result.get(0);
        assertEquals(getTestColorEntity().getColorName(), dto.getColorName());
    }

    @Test
    public void getByIdTest() throws ProductException {
        lenient().when(colorRepository.findById(anyInt())).thenReturn(getTestColorEntity());
        when(colorMapper.toDto(any())).thenReturn(getTestColorDto());
        ColorDto result = colorService.getById(1);
        assertEquals(getTestColorEntity().getColorName(), result.getColorName());
    }

    @Test
    public void getByNameTest() throws ProductException {
        when(colorRepository.findByName(anyString())).thenReturn(getTestColorEntity());
        when(colorMapper.toDto(any())).thenReturn(getTestColorDto());
        ColorDto result = colorService.getByName("test");
        assertEquals(getTestColorEntity().getColorName(), result.getColorName());
    }
}
