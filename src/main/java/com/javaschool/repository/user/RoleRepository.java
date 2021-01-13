package com.javaschool.repository.user;

import com.javaschool.entity.Role;
import com.javaschool.entity.User;

import java.util.Collection;

public interface RoleRepository {

    /**
     * Find by id role entity.
     *
     * @param name the name
     * @return the role entity
     */
    Role findByName(String name);

    /**
     * Find roles by user email list.
     *
     * @param email the user login
     * @return the list
     */
    Collection<Role> findRolesByUserEmail(String email);


    void add(Role role);
}
