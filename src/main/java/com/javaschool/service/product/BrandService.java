package com.javaschool.service.product;

import com.javaschool.dto.product.BrandDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * The interface Brand service.
 *
 * @author Marat Ialyshev
 */
public interface BrandService {

    /**
     * Find all list.
     * @return the list
     */
    List<BrandDto> getAll();

    /**
     * Find by id brand entity.
     *
     * @param id the id
     * @return the brand entity
     */
    BrandDto getById(long id);

    /**
     * Find by name brand entity.
     *
     * @param brandName the brand name
     * @return the brand entity
     */
    BrandDto getByName(String brandName);

    /**
     * Add brand entity.
     *
     * @param brandDto the brand entity
     */
    void addBrand(BrandDto brandDto);

    /**
     * Controller for get all brands.
     *
     * @param model the model on which we put the required data
     */
    void getAllBrandsController(Model model);

    /**
     * Controller for adding brand.
     *
     * @param bindingResult bindingResult
     * @param brandDto the brand entity
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String addNewBrandController(BindingResult bindingResult, BrandDto brandDto, Model model);
}
