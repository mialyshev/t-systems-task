package com.javaschool.repository.impl.product;

import com.javaschool.entity.Product;
import com.javaschool.entity.Product_;
import com.javaschool.repository.product.ProductRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery
                .select(root);
        TypedQuery<Product> selectAll = entityManager.createQuery(criteriaQuery);

        return selectAll.getResultList();
    }

    @Override
    public List<Product> findAllActive() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(Product_.active), true));
        TypedQuery<Product> selectAll = entityManager.createQuery(criteriaQuery);

        return selectAll.getResultList();
    }

    @Override
    public Product findById(long id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    public void save(Product product) {
        entityManager.persist(product);
    }
}
