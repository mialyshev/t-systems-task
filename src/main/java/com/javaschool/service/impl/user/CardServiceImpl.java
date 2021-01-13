package com.javaschool.service.impl.user;

import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.entity.Card;
import com.javaschool.entity.User;
import com.javaschool.repository.user.CardRepository;
import com.javaschool.service.user.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl implements CardService  {

    private final CardRepository cardRepository;

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

    private LocalDate getDate(String date){
        String[] nums = date.split("-");
        return LocalDate.of(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]), 1);
    }
}
