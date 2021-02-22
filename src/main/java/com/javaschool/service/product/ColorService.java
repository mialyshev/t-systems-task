package com.javaschool.service.product;

import com.javaschool.dto.product.ColorDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * The interface Color service.
 *
 * @author Marat Ialyshev
 */
public interface ColorService {

    /**
     * Find all list.
     * @return the list
     */
    List<ColorDto> getAll();

    /**
     * Find by id color entity.
     *
     * @param id the id
     * @return the color entity
     */
    ColorDto getById(long id);

    /**
     * Find by name color entity.
     *
     * @param colorName the color name
     * @return the color entity
     */
    ColorDto getByName(String colorName);

    /**
     * Add color entity.
     *
     * @param colorDto the color entity
     */
    void addColor(ColorDto colorDto);

    /**
     * Controller for get all colors.
     *
     * @param model the model on which we put the required data
     */
    void getAllColorsController(Model model);

    /**
     * Controller for adding color.
     *
     * @param bindingResult bindingResult
     * @param colorDto the color entity
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String addNewColorController(BindingResult bindingResult, ColorDto colorDto, Model model);
}
