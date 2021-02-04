package com.javaschool.repository.impl.product;

import com.javaschool.entity.Material;
import com.javaschool.entity.Material_;
import com.javaschool.exception.ProductException;
import com.javaschool.repository.product.MaterialRepository;
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
public class MaterialRepositoryImpl implements MaterialRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Material> findAll() throws ProductException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Material> criteriaQuery = criteriaBuilder.createQuery(Material.class);
            Root<Material> root = criteriaQuery.from(Material.class);

            criteriaQuery
                    .select(root);
            TypedQuery<Material> selectAll = entityManager.createQuery(criteriaQuery);

            return selectAll.getResultList();
        } catch (PersistenceException e) {
            throw new ProductException("Error getting all materials");
        }
    }

    @Override
    public Material findById(long id) throws ProductException {
        try {
            return entityManager.find(Material.class, id);
        } catch (PersistenceException e) {
            throw new ProductException("Error getting material with id: " + id);
        }
    }

    @Override
    public Material findByName(String materialName) throws ProductException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Material> criteriaQuery = criteriaBuilder.createQuery(Material.class);
            Root<Material> root = criteriaQuery.from(Material.class);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get(Material_.materialName), materialName));
            TypedQuery<Material> selectByName = entityManager.createQuery(criteriaQuery);

            return selectByName.getSingleResult();
        } catch (PersistenceException e) {
            throw new ProductException("Error getting material with name: " + materialName);
        }
    }

    @Override
    public void save(Material material) {
        entityManager.persist(material);
    }
}
