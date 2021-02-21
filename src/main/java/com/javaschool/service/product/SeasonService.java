package com.javaschool.service.product;

import com.javaschool.dto.product.SeasonDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * The interface Season service.
 *
 * @author Marat Ialyshev
 */
public interface SeasonService {

    /**
     * Find all list.
     * @return the list
     */
    List<SeasonDto> getAll();

    /**
     * Find by id season entity.
     *
     * @param id the id
     * @return the season entity
     */
    SeasonDto getById(long id);

    /**
     * Find by name season entity.
     *
     * @param seasonName the season name
     * @return the season entity
     */
    SeasonDto getByName(String seasonName);

    /**
     * Add season entity.
     *
     * @param seasonDto the season entity
     */
    void addSeason(SeasonDto seasonDto);

    /**
     * Controller for get all seasons.
     *
     * @param model the model on which we put the required data
     */
    void getAllSeasonsController(Model model);

    /**
     * Controller for adding season.
     *
     * @param bindingResult bindingResult
     * @param seasonDto the season entity
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String addNewSeasonController(BindingResult bindingResult, SeasonDto seasonDto, Model model);
}
