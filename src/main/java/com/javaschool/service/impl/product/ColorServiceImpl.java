package com.javaschool.service.impl.product;

import com.javaschool.dto.product.ColorDto;
import com.javaschool.entity.Color;
import com.javaschool.repository.product.ColorRepository;
import com.javaschool.service.product.ColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    @Override
    @Transactional
    public void addColor(ColorDto colorDto) {
        Color color = new Color();
        color.setColorName(colorDto.getColorName());
        colorRepository.save(color);
    }
}
