package com.javaschool.repository.impl.product;

import com.javaschool.entity.Season;
import com.javaschool.entity.Season_;
import com.javaschool.repository.product.SeasonRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class SeasonRepositoryImpl implements SeasonRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Season findByName(String seasonName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Season> criteriaQuery = criteriaBuilder.createQuery(Season.class);
        Root<Season> root = criteriaQuery.from(Season.class);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(Season_.seasonName), seasonName));
        TypedQuery<Season> selectByName = entityManager.createQuery(criteriaQuery);

        return selectByName.getSingleResult();
    }

    @Override
    public void save(Season season) {
        entityManager.persist(season);
    }
}
