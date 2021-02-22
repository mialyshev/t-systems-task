package com.javaschool.repository.user;

import com.javaschool.entity.Card;
import com.javaschool.exception.ProductException;
import com.javaschool.exception.UserException;

import java.util.List;

/**
 * The interface Card repository.
 *
 * @author Marat Ialyshev
 */
public interface CardRepository {

    /**
     * Find by id card entity.
     *
     * @param id the id
     * @exception UserException if card not found
     * @return the product entity
     */
    Card findById(long id) throws UserException;

    /**
     * Add card entity.
     *
     * @param card the card entity
     */
    void save(Card card);

    /**
     * Search for all saved user cards.
     *
     * @param userId User id
     * @exception UserException if card not found
     * @return the list
     */
    List<Card> findAllByUserId(long userId) throws UserException;
}
