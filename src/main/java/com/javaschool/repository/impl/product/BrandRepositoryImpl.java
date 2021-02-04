package com.javaschool.repository.impl.product;

import com.javaschool.entity.Brand;
import com.javaschool.entity.Brand_;
import com.javaschool.exception.ProductException;
import com.javaschool.repository.product.BrandRepository;
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
public class BrandRepositoryImpl implements BrandRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Brand> findAll() throws ProductException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
            Root<Brand> root = criteriaQuery.from(Brand.class);

            criteriaQuery
                    .select(root);
            TypedQuery<Brand> selectAll = entityManager.createQuery(criteriaQuery);
            return selectAll.getResultList();
        } catch (PersistenceException e) {
            throw new ProductException("Error getting all entities");
        }
    }

    @Override
    public Brand findById(long id) throws ProductException {
        try {
            return entityManager.find(Brand.class, id);
        } catch (PersistenceException e) {
            throw new ProductException("Error getting brand with id: " + id);
        }
    }

    @Override
    public Brand findByName(String brandName) throws ProductException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
            Root<Brand> root = criteriaQuery.from(Brand.class);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get(Brand_.brandName), brandName));
            TypedQuery<Brand> selectByName = entityManager.createQuery(criteriaQuery);

            return selectByName.getSingleResult();
        } catch (PersistenceException e) {
            throw new ProductException("Error getting brand with name: " + brandName);
        }
    }

    @Override
    public void save(Brand brand) {
        entityManager.persist(brand);
    }
}
