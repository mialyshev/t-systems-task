package com.javaschool.repository.user;

import com.javaschool.entity.User;
import com.javaschool.exception.UserException;

import java.util.List;

public interface UserRepository {

    List<User> findAll() throws UserException;

    User findById(long id) throws UserException;

    User findByEmail(String email) throws UserException;

    void save(User user);

    void update(User user);
}
