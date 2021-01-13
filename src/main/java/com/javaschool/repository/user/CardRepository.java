package com.javaschool.repository.user;

import com.javaschool.entity.Address;
import com.javaschool.entity.Card;

import java.util.List;

public interface CardRepository {

    Card findById(long id);

    void save(Card card);

    List<Card> findAllByUserId(long userId);
}
