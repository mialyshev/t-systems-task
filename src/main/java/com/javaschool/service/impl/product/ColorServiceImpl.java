package com.javaschool.service.impl.product;

import com.javaschool.dto.product.ColorDto;
import com.javaschool.entity.Color;
import com.javaschool.mapper.product.ColorMapperImpl;
import com.javaschool.repository.product.ColorRepository;
import com.javaschool.service.product.ColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;
    private final ColorMapperImpl colorMapper;

    @Override
    public List<ColorDto> getAll() {
        List<ColorDto> colorDtoList = null;
        try {
            colorDtoList = colorMapper.toDtoList(colorRepository.findAll());
        } catch (Exception e) {
            log.error("Error getting all the colors", e);
        }
        return colorDtoList;
    }

    @Override
    public ColorDto getById(long id) {
        ColorDto colorDto = null;
        try {
            colorDto = colorMapper.toDto(colorRepository.findById(id));
        } catch (Exception e) {
            log.error("Error getting a color by id", e);
        }
        return colorDto;
    }

    @Override
    @Transactional
    public ColorDto getByName(String colorName) {
        ColorDto colorDto = null;
        try {
            colorDto = colorMapper.toDto(colorRepository.findByName(colorName));
        } catch (Exception e) {
            log.error("Error getting a color by name", e);
        }
        return colorDto;
    }

    @Override
    @Transactional
    public void addColor(ColorDto colorDto) {
        Color color = new Color();
        color.setColorName(colorDto.getColorName());
        colorRepository.save(color);
    }
}
