package com.javaschool.repository.impl.user;

import com.javaschool.entity.Card;
import com.javaschool.entity.Card_;
import com.javaschool.exception.UserException;
import com.javaschool.repository.user.CardRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CardRepositoryImpl implements CardRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Card findById(long id) throws UserException {
        try {
            return entityManager.find(Card.class, id);
        } catch (PersistenceException e) {
            throw new UserException("Error getting card with id: " + id);
        }
    }

    @Override
    public void save(Card card) {
        entityManager.persist(card);
    }

    @Override
    public List<Card> findAllByUserId(long userId) throws UserException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Card> criteriaQuery = criteriaBuilder.createQuery(Card.class);
            Root<Card> root = criteriaQuery.from(Card.class);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get(Card_.user).get("id"), userId));
            TypedQuery<Card> selectAll = entityManager.createQuery(criteriaQuery);

            return selectAll.getResultList();
        } catch (PersistenceException e) {
            throw new UserException("Error getting cards for user with id: " + userId);
        }
    }
}
