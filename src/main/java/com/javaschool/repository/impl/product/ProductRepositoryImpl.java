package com.javaschool.repository.impl.product;

import com.javaschool.entity.*;
import com.javaschool.entity.Order;
import com.javaschool.repository.impl.product.filtration.ProductSearchQueryCriteriaConsumer;
import com.javaschool.repository.impl.product.filtration.SearchCriteria;
import com.javaschool.repository.product.ProductRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<Product> getProductByOrderId(long orderId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        Join<Product, Order> productOrderJoin = root.join(Product_.orderSet);

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
    public List<Product> findByParam(List<SearchCriteria> params) {
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
    }


}
