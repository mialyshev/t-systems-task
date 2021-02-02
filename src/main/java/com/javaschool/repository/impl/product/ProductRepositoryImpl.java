package com.javaschool.repository.impl.product;

import com.javaschool.entity.*;
import com.javaschool.entity.Order;
import com.javaschool.exception.ProductException;
import com.javaschool.repository.impl.product.filtration.ProductSearchQueryCriteriaConsumer;
import com.javaschool.repository.impl.product.filtration.SearchCriteria;
import com.javaschool.repository.product.ProductRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findAll() throws ProductException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);

            criteriaQuery
                    .select(root);
            TypedQuery<Product> selectAll = entityManager.createQuery(criteriaQuery);

            return selectAll.getResultList();
        }catch (PersistenceException e){
            throw new ProductException("Error getting all products");
        }
    }

    @Override
    public List<Product> findAllActive() throws ProductException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get(Product_.active), true));
            TypedQuery<Product> selectAll = entityManager.createQuery(criteriaQuery);

            return selectAll.getResultList();
        }catch (PersistenceException e){
            throw new ProductException("Error getting all active products");
        }
    }

    @Override
    public List<Product> findAllActiveByModel(String model) throws ProductException  {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get(Product_.active), true),
                            criteriaBuilder.equal(root.get(Product_.model), model));
            TypedQuery<Product> selectAll = entityManager.createQuery(criteriaQuery);

            return selectAll.getResultList();
        }catch (PersistenceException e){
            throw new ProductException("Error getting all active products by model: " + model);
        }
    }

    @Override
    public Product findById(long id) throws ProductException  {
        try {
            return entityManager.find(Product.class, id);
        }catch (PersistenceException e){
            throw new ProductException("Error getting product with id: " + id);
        }
    }

    @Override
    public void save(Product product) {
        entityManager.persist(product);
    }

    @Override
    public List<Product> getProductByOrderId(long orderId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        Join<Product, Order> productOrderJoin = root.join(Product_.orderList);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(productOrderJoin.get(Order_.id), orderId));
        TypedQuery<Product> findAllProductByOrderId = entityManager.createQuery(criteriaQuery);

        return findAllProductByOrderId.getResultStream().collect(Collectors.toList());
    }

    @Override
    public void updateProduct(Product product) {
        entityManager.merge(product);
    }

    @Override
    public List<Product> findByParam(List<SearchCriteria> params) throws ProductException  {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);

            Predicate predicate = criteriaBuilder.conjunction();

            ProductSearchQueryCriteriaConsumer productConsumer = new ProductSearchQueryCriteriaConsumer(predicate, criteriaBuilder, root);

            params.stream().forEach(productConsumer);

            predicate = productConsumer.getPredicate();

            criteriaQuery.where(criteriaBuilder.equal(root.get(Product_.active), true),
                    predicate);

            List<Product> result = entityManager.createQuery(criteriaQuery).getResultList();

            return result;
        }catch (PersistenceException e){
            throw new ProductException("Error getting products with params");
        }
    }


}
