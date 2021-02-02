package com.javaschool.repository.impl.product;

import com.javaschool.entity.Color;
import com.javaschool.entity.Color_;
import com.javaschool.exception.ProductException;
import com.javaschool.repository.product.ColorRepository;
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
public class ColorRepositoryImpl implements ColorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Color> findAll() throws ProductException   {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Color> criteriaQuery = criteriaBuilder.createQuery(Color.class);
            Root<Color> root = criteriaQuery.from(Color.class);

            criteriaQuery
                    .select(root);
            TypedQuery<Color> selectAll = entityManager.createQuery(criteriaQuery);

            return selectAll.getResultList();
        }catch (PersistenceException e){
            throw new ProductException("Error getting all colors");
        }
    }

    @Override
    public Color findById(long id) throws ProductException  {
        try {
            return entityManager.find(Color.class, id);
        }catch (PersistenceException e){
            throw new ProductException("Error getting color with id: " + id);
        }
    }

    @Override
    public Color findByName(String colorName) throws ProductException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Color> criteriaQuery = criteriaBuilder.createQuery(Color.class);
            Root<Color> root = criteriaQuery.from(Color.class);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get(Color_.colorName), colorName));
            TypedQuery<Color> selectByName = entityManager.createQuery(criteriaQuery);

            return selectByName.getSingleResult();
        }catch (PersistenceException e){
            throw new ProductException("Error getting color with name: " + colorName);
        }
    }

    @Override
    public void save(Color color) {
        entityManager.persist(color);
    }
}
