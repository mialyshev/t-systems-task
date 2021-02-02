package com.javaschool.service.product;

import com.javaschool.dto.product.SeasonDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface SeasonService {

    List<SeasonDto> getAll();

    SeasonDto getById(long id);

    SeasonDto getByName(String seasonName);

    void addSeason(SeasonDto seasonDto);

    void getAllSeasonsController(Model model);

    String addNewSeasonController(BindingResult bindingResult, SeasonDto seasonDto, Model model);
}
