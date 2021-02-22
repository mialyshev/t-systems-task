package com.javaschool.service.impl.order;

import com.javaschool.dto.order.AddressAdditionDto;
import com.javaschool.dto.order.AddressDto;
import com.javaschool.entity.Address;
import com.javaschool.entity.User;
import com.javaschool.exception.UserException;
import com.javaschool.mapper.order.AddressMapperImpl;
import com.javaschool.repository.order.AddressRepository;
import com.javaschool.repository.user.UserRepository;
import com.javaschool.service.impl.admin.AdminServiceImpl;
import com.javaschool.service.order.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapperImpl addressMapper;
    private final UserRepository userRepository;

    private static Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Override
    @Transactional
    public void addAddress(AddressAdditionDto addressAdditionDto, User user) {
        log.info("Add new address in address service");
        Address address = new Address();
        try {
            address.setCountry(addressAdditionDto.getCountry());
            address.setCity(addressAdditionDto.getCity());
            address.setPostalCode(addressAdditionDto.getPostalCode());
            address.setStreet(addressAdditionDto.getStreet());
            address.setHouseNumber(addressAdditionDto.getHouseNumber());
            address.setApartamentNumber(addressAdditionDto.getApartamentNumber());
            address.setSaved(addressAdditionDto.isSaved());
            address.setUser(user);
            addressRepository.save(address);
        } catch (Exception e) {
            log.error("Error saved new address", e);
        }
    }

    @Override
    public List<AddressDto> getAllSaved(long userId) {
        log.info("Get all saved address for user with id: " + userId);
        List<AddressDto> addressDtoList = null;
        try {
            addressDtoList = addressMapper.toDtoList(addressRepository.findAllSavedByUserId(userId));
        } catch (UserException e) {
            log.error("Error getting all saved address", e);
        } catch (Exception e) {
            log.error("Error at AddressService.getAllSaved()", e);
        }
        return addressDtoList;
    }

    @Override
    public AddressDto getById(long id) {
        log.info("Get address with id: " + id);
        AddressDto addressDto = null;
        try {
            addressDto = addressMapper.toDto(addressRepository.findById(id));
        } catch (UserException e) {
            log.error("Error getting a address by id", e);
        } catch (Exception e) {
            log.error("Error at AddressService.getById()", e);
        }
        return addressDto;
    }

    @Override
    @Transactional
    public AddressDto getLastByUserId(long userId) {
        log.info("Get the last added user address with id: " + userId);
        AddressDto addressDto = null;
        try {
            addressDto = addressMapper.toDto(addressRepository.getLastByUserId(userId));
        } catch (UserException e) {
            log.error("Error getting a last address by user id", e);
        } catch (Exception e) {
            log.error("Error at AddressService.getLastByUserId()", e);
        }
        return addressDto;
    }

    @Override
    public List<AddressDto> getAll() {
        log.info("Get list with all addresses");
        List<AddressDto> addressDtoList = null;
        try {
            addressDtoList = addressMapper.toDtoList(addressRepository.findAll());
        } catch (UserException e) {
            log.error("Error getting all address", e);
        } catch (Exception e) {
            log.error("Error at AddressService.getAll()", e);
        }
        return addressDtoList;
    }

    @Override
    @Transactional
    public void updateAddress(AddressDto addressDto) {
        log.info("Update address");
        Address address = null;
        try {
            address = addressRepository.findById(addressDto.getId());
            address.setCountry(addressDto.getCountry());
            address.setCity(addressDto.getCity());
            address.setStreet(addressDto.getStreet());
            address.setHouseNumber(addressDto.getHouseNumber());
            address.setApartamentNumber(addressDto.getApartamentNumber());
            address.setPostalCode(addressDto.getPostalCode());
            addressRepository.update(address);
        } catch (UserException e) {
            log.error("Error update address", e);
        } catch (Exception e) {
            log.error("Error at AddressService.updateAddress()", e);
        }
    }

    @Override
    @Transactional
    public void addUpdateAddress(AddressDto addressDto, User user) {
        log.info("Saving the updated address");
        try {
            addAddress(addressMapper.toAdditionDto(addressDto), user);
        } catch (Exception e) {
            log.error("Error add update address", e);
        }

    }

    @Override
    @Transactional
    public void updateSavedAddress(long addressId) {
        log.info("Updating a saved address");
        try {
            Address address = addressRepository.findById(addressId);
            address.setSaved(false);
            addressRepository.update(address);
        } catch (UserException e) {
            log.error("Error update saved address", e);
        } catch (Exception e) {
            log.error("Error at AddressService.updateSavedAddress()", e);
        }

    }

    @Override
    @Transactional
    public void deleteAddress(long addressId) {
        log.info("Deleting address with id: " + addressId);
        try {
            Address address = addressRepository.findById(addressId);
            address.setSaved(false);
            addressRepository.update(address);
        } catch (UserException e) {
            log.error("Error delete address", e);
        } catch (Exception e) {
            log.error("Error at AddressService.deleteAddress()", e);
        }

    }

    @Override
    @Transactional
    public String addAddressController(BindingResult bindingResult, AddressAdditionDto addressAdditionDto) {
        log.info("Retrieving information about a new address to save it");
        if (bindingResult.hasErrors()) {
            return "address";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        User userFromBd = null;
        try {
            userFromBd = userRepository.findByEmail(currentUser);
        } catch (UserException e) {
            log.error("Error while getting user", e);
        }
        addressAdditionDto.setSaved(true);
        addAddress(addressAdditionDto, userFromBd);
        return "redirect:/profile/addresses";
    }
}
