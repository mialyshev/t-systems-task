package com.javaschool.service.user;

import com.javaschool.dto.card.CardDto;
import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.entity.User;

import java.util.List;

public interface CardService {

    void addCard(CardRegisterDto cardDto, User userOwner);

    List<CardDto> getAllByUserId(long userId);

    CardDto getById(long id);
}
