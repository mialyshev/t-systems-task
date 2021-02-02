package com.javaschool.repository.impl.order;

import com.javaschool.entity.Address;
import com.javaschool.entity.Address_;
import com.javaschool.entity.Order;
import com.javaschool.entity.Order_;
import com.javaschool.entity.enums.OrderStatus;
import com.javaschool.exception.OrderException;
import com.javaschool.exception.UserException;
import com.javaschool.repository.order.OrderRepository;
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
public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Order> findAll() throws OrderException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
            Root<Order> root = criteriaQuery.from(Order.class);

            criteriaQuery
                    .select(root);
            TypedQuery<Order> selectAll = entityManager.createQuery(criteriaQuery);

            return selectAll.getResultList();
        }catch (PersistenceException e){
            throw new OrderException("Error all orders");
        }
    }

    @Override
    public Order findById(long id) throws OrderException{
        try {
            return entityManager.find(Order.class, id);
        }catch (PersistenceException e){
            throw new OrderException("Error get order with id: " + id);
        }
    }

    @Override
    public void save(Order order) {
        entityManager.persist(order);
    }

    @Override
    public void updateOrder(Order order) {
        entityManager.merge(order);
    }

    @Override
    public List<Order> findByUserId(long userId) throws OrderException{
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
            Root<Order> root = criteriaQuery.from(Order.class);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get(Order_.user).get("id"), userId));
            TypedQuery<Order> selectAll = entityManager.createQuery(criteriaQuery);

            return selectAll.getResultList();
        }catch (PersistenceException e){
            throw new OrderException("Error get all orders for user with id: " + userId);
        }
    }

    @Override
    public List<Order> findByAddressId(long addressId) throws OrderException{
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
            Root<Order> root = criteriaQuery.from(Order.class);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get(Order_.address).get("id"), addressId));
            TypedQuery<Order> selectAll = entityManager.createQuery(criteriaQuery);

            return selectAll.getResultList();
        }catch (PersistenceException e){
            throw new OrderException("Error get all orders with address id: " + addressId);
        }
    }

    @Override
    public List<Order> findAllDeliveredByUserId(long userId, boolean isDelivered) throws OrderException {
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
            Root<Order> root = criteriaQuery.from(Order.class);
            if (isDelivered) {
                criteriaQuery
                        .select(root)
                        .where(criteriaBuilder.equal(root.get(Order_.user).get("id"), userId),
                                criteriaBuilder.equal(root.get(Order_.orderStatus), OrderStatus.DELIVERED));
            } else {
                criteriaQuery
                        .select(root)
                        .where(criteriaBuilder.equal(root.get(Order_.user).get("id"), userId),
                                criteriaBuilder.notEqual(root.get(Order_.orderStatus), OrderStatus.DELIVERED));
            }
            TypedQuery<Order> selectAll = entityManager.createQuery(criteriaQuery);

            return selectAll.getResultList();
        }catch (PersistenceException e){
            throw new OrderException("Error get all delivered orders for user with id: " + userId);
        }
    }

    @Override
    public List<Order> findAllDelivered(boolean isDelivered) throws OrderException{
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
            Root<Order> root = criteriaQuery.from(Order.class);
            if (isDelivered) {
                criteriaQuery
                        .select(root)
                        .where(criteriaBuilder.equal(root.get(Order_.orderStatus), OrderStatus.DELIVERED));
            } else {
                criteriaQuery
                        .select(root)
                        .where(criteriaBuilder.notEqual(root.get(Order_.orderStatus), OrderStatus.DELIVERED));
            }
            TypedQuery<Order> selectAll = entityManager.createQuery(criteriaQuery);

            return selectAll.getResultList();
        }catch (PersistenceException e){
            throw new OrderException("Error get all delivered orders");
        }
    }


}
