package com.javaschool.service.impl.product;

import com.javaschool.dto.product.SeasonDto;
import com.javaschool.entity.Season;
import com.javaschool.exception.ProductException;
import com.javaschool.mapper.product.SeasonMapperImpl;
import com.javaschool.repository.product.SeasonRepository;
import com.javaschool.service.product.SeasonService;
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
public class SeasonServiceImpl implements SeasonService {

    private final SeasonRepository seasonRepository;
    private final SeasonMapperImpl seasonMapper;

    @Override
    public List<SeasonDto> getAll() {
        List<SeasonDto> seasonDtoList = null;
        try {
            seasonDtoList = seasonMapper.toDtoList(seasonRepository.findAll());
        } catch (ProductException e) {
            log.error("Error getting all the seasons", e);
        } catch (Exception e) {
            log.error("Error at SeasonService.getAll()", e);
        }
        return seasonDtoList;
    }

    @Override
    public SeasonDto getById(long id) {
        SeasonDto seasonDto = null;
        try {
            seasonDto = seasonMapper.toDto(seasonRepository.findById(id));
        } catch (ProductException e) {
            log.error("Error getting a season by id", e);
        } catch (Exception e) {
            log.error("Error at SeasonService.getById()", e);
        }
        return seasonDto;
    }

    @Override
    @Transactional
    public SeasonDto getByName(String seasonName) {
        SeasonDto seasonDto = null;
        try {
            seasonDto = seasonMapper.toDto(seasonRepository.findByName(seasonName));
        } catch (ProductException e) {
            log.error("Error getting a season by name", e);
        } catch (Exception e) {
            log.error("Error at SeasonService.getByName()", e);
        }
        return seasonDto;
    }

    @Override
    @Transactional
    public void addSeason(SeasonDto seasonDto) {
        Season season = new Season();
        season.setSeasonName(seasonDto.getSeasonName());
        seasonRepository.save(season);
    }

    @Override
    public void getAllSeasonsController(Model model) {
        model.addAttribute("seasons", getAll());
        model.addAttribute("seasonForm", new SeasonDto());
    }

    @Override
    @Transactional
    public String addNewSeasonController(BindingResult bindingResult, SeasonDto seasonDto, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("seasons", getAll());
            return "admin-season";
        }
        if (getByName(seasonDto.getSeasonName()) != null) {
            model.addAttribute("seasonError", "A season with the same name already exists");
            List<SeasonDto> seasons = getAll();
            model.addAttribute("seasons", seasons);
            return "admin-season";
        }
        addSeason(seasonDto);
        return "redirect:/product/season";
    }
}
