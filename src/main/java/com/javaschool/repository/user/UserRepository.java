package com.javaschool.repository.user;

import com.javaschool.entity.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findById(long id);

    User findByEmail(String email);

    void save(User user);
}
