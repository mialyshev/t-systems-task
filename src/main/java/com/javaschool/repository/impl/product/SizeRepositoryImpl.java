package com.javaschool.repository.impl.product;

import com.javaschool.entity.Size;
import com.javaschool.entity.Size_;
import com.javaschool.exception.ProductException;
import com.javaschool.repository.product.SizeRepository;
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
public class SizeRepositoryImpl implements SizeRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Size> findAll() throws ProductException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Size> criteriaQuery = criteriaBuilder.createQuery(Size.class);
            Root<Size> root = criteriaQuery.from(Size.class);

            criteriaQuery
                    .select(root);
            TypedQuery<Size> selectAll = entityManager.createQuery(criteriaQuery);

            return selectAll.getResultList();
        } catch (PersistenceException e) {
            throw new ProductException("Error getting all sizes");
        }
    }

    @Override
    public Size findById(long id) throws ProductException {
        try {
            return entityManager.find(Size.class, id);
        } catch (PersistenceException e) {
            throw new ProductException("Error getting size with id: " + id);
        }
    }

    @Override
    public Size findBySize(float size) throws ProductException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Size> criteriaQuery = criteriaBuilder.createQuery(Size.class);
            Root<Size> root = criteriaQuery.from(Size.class);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get(Size_.size), size));
            TypedQuery<Size> selectByName = entityManager.createQuery(criteriaQuery);

            return selectByName.getSingleResult();
        } catch (PersistenceException e) {
            throw new ProductException("Error getting size: " + size);
        }
    }

    @Override
    public void save(Size size) {
        entityManager.persist(size);
    }
}
