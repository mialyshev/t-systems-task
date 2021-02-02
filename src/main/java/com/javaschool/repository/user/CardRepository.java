package com.javaschool.repository.user;

import com.javaschool.entity.Address;
import com.javaschool.entity.Card;
import com.javaschool.exception.UserException;

import java.util.List;

public interface CardRepository {

    Card findById(long id) throws UserException;

    void save(Card card);

    List<Card> findAllByUserId(long userId) throws UserException;
}
