package com.javaschool.mapper.user;

import com.javaschool.dto.card.CardDto;
import com.javaschool.entity.Card;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CardMapperImpl {

    public CardDto toDto(Card card){
        if(card == null){
            return null;
        }

        CardDto.CardDtoBuilder cardDto = CardDto.builder();
        cardDto.id(card.getId());
        cardDto.code(card.getCode());
        cardDto.owner(card.getOwner());
        cardDto.validatyDate(card.getValidatyDate());
        cardDto.number(card.getNumber());

        return cardDto.build();
    }

    public List<CardDto> toDtoList(List<Card> cardList) {
        if (cardList == null) {
            return null;
        }

        List<CardDto> list = new ArrayList<CardDto>(cardList.size());
        for (Card card : cardList) {
            list.add(toDto(card));
        }

        return list;
    }

}
