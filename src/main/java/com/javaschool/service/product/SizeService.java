package com.javaschool.service.product;

import com.javaschool.dto.product.SizeDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * The interface Size service.
 *
 * @author Marat Ialyshev
 */
public interface SizeService {

    /**
     * Find all list.
     * @return the list
     */
    List<SizeDto> getAll();

    /**
     * Find by id size entity.
     *
     * @param id the id
     * @return the size entity
     */
    SizeDto getById(long id);

    /**
     * Find by name size entity.
     *
     * @param size the value of size
     * @return the size entity
     */
    SizeDto getByName(float size);

    /**
     * Add size entity.
     *
     * @param sizeDto the size entity
     */
    void addSize(SizeDto sizeDto);

    /**
     * Controller for get all sizes.
     *
     * @param model the model on which we put the required data
     */
    void getAllSizesController(Model model);

    /**
     * Controller for adding size.
     *
     * @param bindingResult bindingResult
     * @param sizeDto the size entity
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String addNewSizeController(BindingResult bindingResult, SizeDto sizeDto, Model model);
}
