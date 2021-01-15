package com.javaschool.service.impl.user;

import com.javaschool.dto.card.CardDto;
import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.dto.product.BrandDto;
import com.javaschool.entity.Card;
import com.javaschool.entity.User;
import com.javaschool.mapper.user.CardMapperImpl;
import com.javaschool.repository.user.CardRepository;
import com.javaschool.service.user.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl implements CardService  {

    private final CardRepository cardRepository;
    private final CardMapperImpl cardMapper;

    @Override
    @Transactional
    public void addCard(CardRegisterDto cardDto, User userOwner) {
        Card card = new Card();
        card.setNumber(cardDto.getNumber());
        card.setCode(cardDto.getCode());
        card.setOwner(cardDto.getOwner());
        card.setValidatyDate(getDate(cardDto.getValidatyDate()));
        card.setUser(userOwner);
        cardRepository.save(card);
    }

    @Override
    public List<CardDto> getAllByUserId(long userId) {
        List<CardDto> cardDtos = null;
        try {
            cardDtos = cardMapper.toDtoList(cardRepository.findAllByUserId(userId));
        } catch (Exception e) {
            log.error("Error getting all saved card", e);
        }
        return cardDtos;
    }

    @Override
    public CardDto getById(long id) {
        CardDto cardDto = null;
        try {
            cardDto = cardMapper.toDto(cardRepository.findById(id));
        } catch (Exception e) {
            log.error("Error getting a card by id", e);
        }
        return cardDto;
    }

    private LocalDate getDate(String date){
        String[] nums = date.split("-");
        return LocalDate.of(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]), 1);
    }
}
