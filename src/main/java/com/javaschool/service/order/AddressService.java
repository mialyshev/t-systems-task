package com.javaschool.service.order;

import com.javaschool.dto.order.AddressAdditionDto;
import com.javaschool.dto.order.AddressDto;
import com.javaschool.entity.User;
import com.javaschool.exception.UserException;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * The interface Address service.
 *
 * @author Marat Ialyshev
 */
public interface AddressService {

    /**
     * Add address entity.
     *
     * @param additionDto the address entity
     * @param user user who adds the address
     */
    void addAddress(AddressAdditionDto additionDto, User user);

    /**
     * Search for all saved user addresses.
     *
     * @param id user id
     * @return the list
     */
    List<AddressDto> getAllSaved(long id);

    /**
     * Find by id address entity.
     *
     * @param id the id
     * @return the address entity
     */
    AddressDto getById(long id);

    /**
     * Returns the last added user address.
     *
     * @param userId User id
     * @return the address entity
     */
    AddressDto getLastByUserId(long userId);

    /**
     * Find all list.
     * @return the list
     */
    List<AddressDto> getAll();

    /**
     * Update address entity.
     *
     * @param addressDto the address entity
     */
    void updateAddress(AddressDto addressDto);

    /**
     * Saving the updated address.
     *
     * @param user user who adds the address
     * @param addressDto the address entity
     */
    void addUpdateAddress(AddressDto addressDto, User user);

    /**
     * Update saved address entity.
     *
     * @param addressId the address id
     */
    void updateSavedAddress(long addressId);

    /**
     * Delete saved address entity.
     *
     * @param addressId the address id
     */
    void deleteAddress(long addressId);

    /**
     * Controller for adding address.
     *
     * @param bindingResult bindingResult
     * @param addressAdditionDto the address entity
     *
     * @return the title of the htm page to display
     */
    String addAddressController(BindingResult bindingResult, AddressAdditionDto addressAdditionDto);
}
