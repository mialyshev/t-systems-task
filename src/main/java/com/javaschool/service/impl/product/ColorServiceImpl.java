package com.javaschool.service.impl.product;

import com.javaschool.dto.product.ColorDto;
import com.javaschool.entity.Color;
import com.javaschool.exception.ProductException;
import com.javaschool.mapper.product.ColorMapperImpl;
import com.javaschool.repository.product.ColorRepository;
import com.javaschool.service.product.ColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;
    private final ColorMapperImpl colorMapper;

    private static Logger log = LoggerFactory.getLogger(ColorServiceImpl.class);

    @Override
    public List<ColorDto> getAll() {
        log.info("Get all colors");
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
        log.info("Get color with id: " + id);
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
        log.info("Get color with name: " + colorName);
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
        log.info("Save new color");
        Color color = new Color();
        color.setColorName(colorDto.getColorName());
        colorRepository.save(color);
    }

    @Override
    public void getAllColorsController(Model model) {
        log.info("Getting all colors to display on the model");
        model.addAttribute("colors", getAll());
        model.addAttribute("colorForm", new ColorDto());
    }

    @Override
    @Transactional
    public String addNewColorController(BindingResult bindingResult, ColorDto colorDto, Model model) {
        log.info("Retrieving information about a new color to save it");
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
