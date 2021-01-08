package com.javaschool.repository;

import com.javaschool.entity.User;

import java.util.List;

public interface UserRepository {

    /**
     * Find all list.
     *
     * @return the list
     */
    List<User> findAll();

    /**
     * Find by id user entity.
     *
     * @param id the id
     * @return the user entity
     */
    User findById(long id);

    /**
     * Find by email user entity.
     *
     * @param email the email
     * @return the user entity
     */
    User findByEmail(String email);

    /**
     * Add user entity.
     *
     * @param user the user entity
     * @return the user entity
     */
    void save(User user);
}
