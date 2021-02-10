package com.javaschool.repository.impl.product;

import com.javaschool.entity.Season;
import com.javaschool.entity.Season_;
import com.javaschool.exception.ProductException;
import com.javaschool.repository.product.SeasonRepository;
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
public class SeasonRepositoryImpl implements SeasonRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Season> findAll() throws ProductException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Season> criteriaQuery = criteriaBuilder.createQuery(Season.class);
            Root<Season> root = criteriaQuery.from(Season.class);

            criteriaQuery
                    .select(root);
            TypedQuery<Season> selectAll = entityManager.createQuery(criteriaQuery);

            return selectAll.getResultList();
        } catch (PersistenceException e) {
            throw new ProductException("Error getting all seasons");
        }
    }

    @Override
    public Season findById(long id) throws ProductException {
        try {
            return entityManager.find(Season.class, id);
        } catch (PersistenceException e) {
            throw new ProductException("Error getting season with id: " + id);
        }
    }

    @Override
    public Season findByName(String seasonName) throws ProductException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Season> criteriaQuery = criteriaBuilder.createQuery(Season.class);
            Root<Season> root = criteriaQuery.from(Season.class);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get(Season_.seasonName), seasonName));
            TypedQuery<Season> selectByName = entityManager.createQuery(criteriaQuery);

            return selectByName.getSingleResult();
        } catch (PersistenceException e) {
            throw new ProductException("Error getting season with name: " + seasonName);
        }
    }

    @Override
    public void save(Season season) {
        entityManager.persist(season);
    }
}
