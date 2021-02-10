package com.javaschool.repository.impl.user;

import com.javaschool.entity.User;
import com.javaschool.entity.User_;
import com.javaschool.exception.UserException;
import com.javaschool.repository.user.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll() throws UserException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);

            criteriaQuery
                    .select(root);
            TypedQuery<User> selectAll = entityManager.createQuery(criteriaQuery);

            return selectAll.getResultList();
        } catch (PersistenceException e) {
            throw new UserException("Error getting all users");
        }
    }

    @Override
    public User findById(long id) throws UserException {
        try {
            return entityManager.find(User.class, id);
        } catch (PersistenceException e) {
            throw new UserException("Error getting user with id: " + id);
        }
    }

    @Override
    public User findByEmail(String email) throws UserException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get(User_.email), email));
            TypedQuery<User> selectByEmail = entityManager.createQuery(criteriaQuery);

            return selectByEmail.getResultStream().findFirst().orElse(null);
        } catch (PersistenceException e) {
            throw new UserException("Error getting user with email: " + email);
        }
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }
}
