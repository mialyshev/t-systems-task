package com.javaschool.service.impl.product;

import com.javaschool.dto.product.ColorDto;
import com.javaschool.entity.Color;
import com.javaschool.exception.ProductException;
import com.javaschool.mapper.product.ColorMapperImpl;
import com.javaschool.repository.product.ColorRepository;
import com.javaschool.service.product.ColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

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
        } catch (ProductException e) {
            log.error("Error getting all the colors", e);
        } catch (Exception e) {
            log.error("Error at ColorService.getAll()", e);
        }
        return colorDtoList;
    }

    @Override
    public ColorDto getById(long id) {
        ColorDto colorDto = null;
        try {
            colorDto = colorMapper.toDto(colorRepository.findById(id));
        } catch (ProductException e) {
            log.error("Error getting a color by id", e);
        } catch (Exception e) {
            log.error("Error at ColorService.getById()", e);
        }
        return colorDto;
    }

    @Override
    @Transactional
    public ColorDto getByName(String colorName) {
        ColorDto colorDto = null;
        try {
            colorDto = colorMapper.toDto(colorRepository.findByName(colorName));
        } catch (ProductException e) {
            log.error("Error getting a color by name", e);
        } catch (Exception e) {
            log.error("Error at ColorService.getByName()", e);
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

    @Override
    public void getAllColorsController(Model model) {
        model.addAttribute("colors", getAll());
        model.addAttribute("colorForm", new ColorDto());
    }

    @Override
    @Transactional
    public String addNewColorController(BindingResult bindingResult, ColorDto colorDto, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("colors", getAll());
            return "admin-color";
        }
        if (getByName(colorDto.getColorName()) != null) {
            model.addAttribute("colorError", "A color with the same name already exists");
            List<ColorDto> colors = getAll();
            model.addAttribute("colors", colors);
            return "admin-color";
        }
        addColor(colorDto);
        return "redirect:/product/color";
    }
}
