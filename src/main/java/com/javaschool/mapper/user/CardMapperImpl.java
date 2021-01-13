package com.javaschool.mapper.user;

import com.javaschool.dto.card.CardDto;
import com.javaschool.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class CardMapperImpl {

    public CardDto toDto(Card card){
        if(card == null){
            return null;
        }

        CardDto.CardDtoBuilder cardDto = CardDto.builder();
        cardDto.code(card.getCode());
        cardDto.owner(card.getOwner());
        cardDto.validatyDate(card.getValidatyDate());
        cardDto.number(card.getNumber());

        return cardDto.build();
    }

}
