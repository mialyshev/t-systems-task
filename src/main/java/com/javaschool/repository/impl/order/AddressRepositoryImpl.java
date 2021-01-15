package com.javaschool.repository.impl.order;

import com.javaschool.entity.Address;
import com.javaschool.entity.Address_;
import com.javaschool.repository.order.AddressRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public List<Address> findAllSavedByUserId(long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> root = criteriaQuery.from(Address.class);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(Address_.user).get("id"), userId),
                        criteriaBuilder.equal(root.get(Address_.isSaved), true));
        TypedQuery<Address> selectAll = entityManager.createQuery(criteriaQuery);

        return selectAll.getResultList();
    }

    @Override
    public Address findById(long id) {
        return entityManager.find(Address.class, id);
    }

    @Override
    public void save(Address address) {
        entityManager.persist(address);
    }

    @Override
    public Address getLastByUserId(long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> root = criteriaQuery.from(Address.class);
        criteriaQuery
                .select(root)
                .orderBy(criteriaBuilder.desc(root.get("id")))
                .where(criteriaBuilder.equal(root.get(Address_.user).get("id"), userId));
        TypedQuery<Address> findAllAddressByUserId = entityManager.createQuery(criteriaQuery);
        return findAllAddressByUserId.getResultStream().findFirst().orElse(null);
    }
}
