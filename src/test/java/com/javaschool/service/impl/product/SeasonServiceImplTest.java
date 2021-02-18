package com.javaschool.service.impl.product;

import com.javaschool.dto.product.SeasonDto;
import com.javaschool.entity.Season;
import com.javaschool.exception.ProductException;
import com.javaschool.mapper.product.SeasonMapperImpl;
import com.javaschool.repository.product.SeasonRepository;
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
public class SeasonServiceImplTest {

    @Mock
    SeasonRepository seasonRepository;

    @Mock
    SeasonMapperImpl seasonMapper;

    @InjectMocks
    SeasonServiceImpl seasonService;

    private Season getTestSeasonEntity() {
        return Season.builder()
                .id(1)
                .seasonName("test")
                .build();
    }

    private SeasonDto getTestSeasonDto() {
        return SeasonDto.builder()
                .seasonName("test")
                .build();
    }


    @Test
    public void getAllTest() throws ProductException {
        when(seasonRepository.findAll()).thenReturn(Collections.singletonList(getTestSeasonEntity()));
        when(seasonMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(getTestSeasonDto()));
        List<SeasonDto> result = seasonService.getAll();
        assertEquals(1, result.size());
        SeasonDto dto = result.get(0);
        assertEquals(getTestSeasonEntity().getSeasonName(), dto.getSeasonName());
    }

    @Test
    public void getByIdTest() throws ProductException {
        lenient().when(seasonRepository.findById(anyInt())).thenReturn(getTestSeasonEntity());
        when(seasonMapper.toDto(any())).thenReturn(getTestSeasonDto());
        SeasonDto result = seasonService.getById(1);
        assertEquals(getTestSeasonEntity().getSeasonName(), result.getSeasonName());
    }

    @Test
    public void getByNameTest() throws ProductException {
        when(seasonRepository.findByName(anyString())).thenReturn(getTestSeasonEntity());
        when(seasonMapper.toDto(any())).thenReturn(getTestSeasonDto());
        SeasonDto result = seasonService.getByName("test");
        assertEquals(getTestSeasonEntity().getSeasonName(), result.getSeasonName());
    }

    @Test
    public void addSeasonTest() {
        seasonService.addSeason(getTestSeasonDto());
        verify(seasonRepository, only()).save(any(Season.class));
    }
}
