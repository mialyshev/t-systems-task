package com.javaschool.service.product;

import com.javaschool.dto.product.MaterialDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * The interface Material service.
 *
 * @author Marat Ialyshev
 */
public interface MaterialService {

    /**
     * Find all list.
     * @return the list
     */
    List<MaterialDto> getAll();

    /**
     * Find by id material entity.
     *
     * @param id the id
     * @return the material entity
     */
    MaterialDto getById(long id);

    /**
     * Find by name material entity.
     *
     * @param materialName the material name
     * @return the material entity
     */
    MaterialDto getByName(String materialName);

    /**
     * Add material entity.
     *
     * @param materialDto the material entity
     */
    void addMaterial(MaterialDto materialDto);

    /**
     * Controller for get all materials.
     *
     * @param model the model on which we put the required data
     */
    void getAllMaterialsController(Model model);

    /**
     * Controller for adding material.
     *
     * @param bindingResult bindingResult
     * @param materialDto the material entity
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String addNewMaterialController(BindingResult bindingResult, MaterialDto materialDto, Model model);
}
