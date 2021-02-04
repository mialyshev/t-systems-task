package com.javaschool.repository.impl.user;

import com.javaschool.entity.Role;
import com.javaschool.entity.Role_;
import com.javaschool.entity.User;
import com.javaschool.entity.User_;
import com.javaschool.exception.UserException;
import com.javaschool.repository.user.RoleRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.stream.Collectors;


@Repository
public class RoleRepositoryImpl implements RoleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role findByName(String name) throws UserException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
            Root<Role> root = criteriaQuery.from(Role.class);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get(Role_.name), name));
            TypedQuery<Role> selectByName = entityManager.createQuery(criteriaQuery);

            return selectByName.getSingleResult();
        } catch (PersistenceException e) {
            throw new UserException("Error getting role with name: " + name);
        }
    }

    @Override
    public Collection<Role> findRolesByUserEmail(String email) throws UserException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
            Root<Role> root = criteriaQuery.from(Role.class);

            Join<Role, User> userEntityJoin = root.join(Role_.userSet);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(userEntityJoin.get(User_.email), email));
            TypedQuery<Role> selectByUserLogin = entityManager.createQuery(criteriaQuery);

            return selectByUserLogin.getResultStream().collect(Collectors.toSet());
        } catch (PersistenceException e) {
            throw new UserException("Error getting roles for user with email: " + email);
        }
    }

    @Override
    public void add(Role role) {
        entityManager.persist(role);
    }
}
