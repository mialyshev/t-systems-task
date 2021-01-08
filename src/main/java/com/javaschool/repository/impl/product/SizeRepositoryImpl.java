package com.javaschool.repository.impl.product;

import com.javaschool.entity.Size;
import com.javaschool.entity.Size_;
import com.javaschool.repository.product.SizeRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public List<Size> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Size> criteriaQuery = criteriaBuilder.createQuery(Size.class);
        Root<Size> root = criteriaQuery.from(Size.class);

        criteriaQuery
                .select(root);
        TypedQuery<Size> selectAll = entityManager.createQuery(criteriaQuery);

        return selectAll.getResultList();
    }

    @Override
    public Size findById(long id) {
        return entityManager.find(Size.class, id);
    }

    @Override
    public Size findBySize(float size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Size> criteriaQuery = criteriaBuilder.createQuery(Size.class);
        Root<Size> root = criteriaQuery.from(Size.class);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(Size_.size), size));
        TypedQuery<Size> selectByName = entityManager.createQuery(criteriaQuery);

        return selectByName.getSingleResult();
    }

    @Override
    public void save(Size size) {
        entityManager.persist(size);
    }
}
