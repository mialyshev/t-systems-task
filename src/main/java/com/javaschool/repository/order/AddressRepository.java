package com.javaschool.repository.order;

import com.javaschool.entity.Address;
import com.javaschool.exception.UserException;

import java.util.List;

/**
 * The interface Address repository.
 *
 * @author Marat Ialyshev
 */
public interface AddressRepository {

    /**
     * Search for all saved user addresses.
     *
     * @param userId User id
     * @exception UserException if address not found
     * @return the list
     */
    List<Address> findAllSavedByUserId(long userId) throws UserException;

    /**
     * Find by id address entity.
     *
     * @param id the id
     * @exception UserException if address not found
     * @return the address entity
     */
    Address findById(long id) throws UserException;

    /**
     * Add address entity.
     *
     * @param address the address entity
     */
    void save(Address address);
    
    /**
     * Returns the last added user address.
     *
     * @param userId User id
     * @exception UserException if address not found
     * @return the address entity
     */
    Address getLastByUserId(long userId) throws UserException;

    /**
     * Find all list.
     * @exception UserException if address not found
     * @return the list
     */
    List<Address> findAll() throws UserException;

    /**
     * Update address entity.
     *
     * @param address the address entity
     */
    void update(Address address);
}
