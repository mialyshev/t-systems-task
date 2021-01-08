package com.javaschool.repository.impl.product;

import com.javaschool.entity.Material;
import com.javaschool.entity.Material_;
import com.javaschool.repository.product.MaterialRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class MaterialRepositoryImpl implements MaterialRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Material> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Material> criteriaQuery = criteriaBuilder.createQuery(Material.class);
        Root<Material> root = criteriaQuery.from(Material.class);

        criteriaQuery
                .select(root);
        TypedQuery<Material> selectAll = entityManager.createQuery(criteriaQuery);

        return selectAll.getResultList();
    }

    @Override
    public Material findById(long id) {
        return entityManager.find(Material.class, id);
    }

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
