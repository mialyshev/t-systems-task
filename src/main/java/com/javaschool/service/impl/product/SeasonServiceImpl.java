package com.javaschool.service.impl.product;

import com.javaschool.dto.product.SeasonDto;
import com.javaschool.entity.Season;
import com.javaschool.repository.SeasonRepository;
import com.javaschool.service.product.SeasonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SeasonServiceImpl implements SeasonService {

    private final SeasonRepository seasonRepository;


    @Override
    @Transactional
    public void addSeason(SeasonDto seasonDto) {
        Season season = new Season();
        season.setSeasonName(seasonDto.getSeasonName());
        seasonRepository.save(season);
    }
}
