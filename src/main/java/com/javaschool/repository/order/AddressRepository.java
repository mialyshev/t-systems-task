package com.javaschool.repository.order;

import com.javaschool.entity.Address;

import java.util.List;

public interface AddressRepository {

    List<Address> findAllSavedByUserId(long userId);

    Address findById(long id);

    void save(Address address);

    Address getLastByUserId(long userId);

    List<Address> findAll();
}
