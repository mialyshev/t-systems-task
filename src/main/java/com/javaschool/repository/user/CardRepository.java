package com.javaschool.repository.user;

import com.javaschool.entity.Card;

public interface CardRepository {

    Card findById(long id);

    void save(Card card);
}
