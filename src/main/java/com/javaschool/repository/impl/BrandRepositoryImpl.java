package com.javaschool.repository.impl;

import com.javaschool.entity.Brand;
import com.javaschool.entity.Brand_;
import com.javaschool.repository.BrandRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class BrandRepositoryImpl implements BrandRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Brand findByName(String brandName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Root<Brand> root = criteriaQuery.from(Brand.class);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(Brand_.brandName), brandName));
        TypedQuery<Brand> selectByName = entityManager.createQuery(criteriaQuery);

        return selectByName.getSingleResult();
    }

    @Override
    public void save(Brand brand) {
        entityManager.persist(brand);
    }
}
