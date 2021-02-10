package com.javaschool.repository.impl.product;

import com.javaschool.entity.Category;
import com.javaschool.entity.Category_;
import com.javaschool.exception.ProductException;
import com.javaschool.repository.product.CategoryRepository;
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
public class CategoryRepositoryImpl implements CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Category> findAll() throws ProductException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
            Root<Category> root = criteriaQuery.from(Category.class);

            criteriaQuery
                    .select(root);
            TypedQuery<Category> selectAll = entityManager.createQuery(criteriaQuery);

            return selectAll.getResultList();
        } catch (PersistenceException e) {
            throw new ProductException("Error getting all categories");
        }
    }

    @Override
    public Category findById(long id) throws ProductException {
        try {
            return entityManager.find(Category.class, id);
        } catch (PersistenceException e) {
            throw new ProductException("Error getting category with id: " + id);
        }
    }

    @Override
    public Category findByName(String categoryName) throws ProductException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
            Root<Category> root = criteriaQuery.from(Category.class);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get(Category_.categoryName), categoryName));
            TypedQuery<Category> selectByName = entityManager.createQuery(criteriaQuery);

            return selectByName.getSingleResult();
        } catch (PersistenceException e) {
            throw new ProductException("Error getting category with name: " + categoryName);
        }
    }

    @Override
    public void save(Category category) {
        entityManager.persist(category);
    }
}
