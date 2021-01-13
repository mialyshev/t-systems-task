package com.javaschool.repository.impl.user;

import com.javaschool.entity.Card;
import com.javaschool.repository.user.CardRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CardRepositoryImpl implements CardRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Card findById(long id) {
        return entityManager.find(Card.class, id);
    }

    @Override
    public void save(Card card) {
        entityManager.persist(card);
    }
}
