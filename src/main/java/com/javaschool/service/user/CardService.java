package com.javaschool.service.user;

import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.entity.User;

public interface CardService {

    void addCard(CardRegisterDto cardDto, User userOwner);
}
