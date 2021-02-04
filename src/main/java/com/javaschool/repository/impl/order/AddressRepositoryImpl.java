package com.javaschool.repository.impl.order;

import com.javaschool.entity.Address;
import com.javaschool.entity.Address_;
import com.javaschool.exception.UserException;
import com.javaschool.repository.order.AddressRepository;
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
public class AddressRepositoryImpl implements AddressRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Address> findAllSavedByUserId(long userId) throws UserException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
            Root<Address> root = criteriaQuery.from(Address.class);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get(Address_.user).get("id"), userId),
                            criteriaBuilder.equal(root.get(Address_.isSaved), true));
            TypedQuery<Address> selectAll = entityManager.createQuery(criteriaQuery);

            return selectAll.getResultList();
        } catch (PersistenceException e) {
            throw new UserException("Error getting saved address for user with id: " + userId);
        }
    }

    @Override
    public Address findById(long id) throws UserException {
        try {
            return entityManager.find(Address.class, id);
        } catch (PersistenceException e) {
            throw new UserException("Error getting address with id: " + id);
        }
    }

    @Override
    public void save(Address address) {
        entityManager.persist(address);
    }

    @Override
    public Address getLastByUserId(long userId) throws UserException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
            Root<Address> root = criteriaQuery.from(Address.class);
            criteriaQuery
                    .select(root)
                    .orderBy(criteriaBuilder.desc(root.get("id")))
                    .where(criteriaBuilder.equal(root.get(Address_.user).get("id"), userId));
            TypedQuery<Address> findAllAddressByUserId = entityManager.createQuery(criteriaQuery);
            return findAllAddressByUserId.getResultStream().findFirst().orElse(null);
        } catch (PersistenceException e) {
            throw new UserException("Error getting last saved address for user with id: " + userId);
        }
    }

    @Override
    public List<Address> findAll() throws UserException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
            Root<Address> root = criteriaQuery.from(Address.class);

            criteriaQuery
                    .select(root);
            TypedQuery<Address> selectAll = entityManager.createQuery(criteriaQuery);

            return selectAll.getResultList();
        } catch (PersistenceException e) {
            throw new UserException("Error getting all address");
        }
    }

    @Override
    public void update(Address address) {
        entityManager.merge(address);
    }
}
