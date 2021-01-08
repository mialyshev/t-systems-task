package com.javaschool.repository.impl;

import com.javaschool.entity.Material;
import com.javaschool.entity.Material_;
import com.javaschool.repository.MaterialRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class MaterialRepositoryImpl implements MaterialRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Material findByName(String materialName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Material> criteriaQuery = criteriaBuilder.createQuery(Material.class);
        Root<Material> root = criteriaQuery.from(Material.class);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(Material_.materialName), materialName));
        TypedQuery<Material> selectByName = entityManager.createQuery(criteriaQuery);

        return selectByName.getSingleResult();
    }

    @Override
    public void save(Material material) {
        entityManager.persist(material);
    }
}
