package com.javaschool.repository.user;

import com.javaschool.entity.User;
import com.javaschool.exception.UserException;

import java.util.List;

/**
 * The interface User repository.
 *
 * @author Marat Ialyshev
 */
public interface UserRepository {

    /**
     * Find all list.
     * @exception UserException if something going wrong
     * @return the list
     */
    List<User> findAll() throws UserException;

    /**
     * Find by id user entity.
     *
     * @param id the id
     * @exception UserException if user not found
     * @return the user entity
     */
    User findById(long id) throws UserException;

    /**
     * Find by email user entity.
     *
     * @param email the email
     * @return the user entity
     */
    User findByEmail(String email) throws UserException;

    /**
     * Add user entity.
     *
     * @param user the user entity
     */
    void save(User user);

    /**
     * Update user entity.
     *
     * @param user the user entity
     */
    void update(User user);
}
