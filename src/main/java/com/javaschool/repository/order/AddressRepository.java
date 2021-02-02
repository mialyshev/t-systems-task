package com.javaschool.repository.order;

import com.javaschool.entity.Address;
import com.javaschool.exception.UserException;

import java.util.List;

public interface AddressRepository {

    List<Address> findAllSavedByUserId(long userId) throws UserException;

    Address findById(long id) throws UserException;

    void save(Address address);

    Address getLastByUserId(long userId) throws UserException;

    List<Address> findAll() throws UserException;

    void update(Address address);
}
