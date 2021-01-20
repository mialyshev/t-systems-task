package com.javaschool.service.impl.order;

import com.javaschool.dto.order.AddressAdditionDto;
import com.javaschool.dto.order.AddressDto;
import com.javaschool.entity.Address;
import com.javaschool.entity.User;
import com.javaschool.mapper.order.AddressMapperImpl;
import com.javaschool.repository.order.AddressRepository;
import com.javaschool.service.order.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapperImpl addressMapper;

    @Override
    @Transactional
    public void addAddress(AddressAdditionDto addressAdditionDto, User user) {
        Address address = new Address();
        address.setCountry(addressAdditionDto.getCountry());
        address.setCity(addressAdditionDto.getCity());
        address.setPostalCode(addressAdditionDto.getPostalCode());
        address.setStreet(addressAdditionDto.getStreet());
        address.setHouseNumber(addressAdditionDto.getHouseNumber());
        address.setApartamentNumber(addressAdditionDto.getApartamentNumber());
        address.setSaved(addressAdditionDto.isSaved());
        address.setUser(user);
        addressRepository.save(address);
    }

    @Override
    public List<AddressDto> getAllSaved(long userId) {
        List<AddressDto> addressDtoList = null;
        try {
            addressDtoList = addressMapper.toDtoList(addressRepository.findAllSavedByUserId(userId));
        } catch (Exception e) {
            log.error("Error getting all saved address", e);
        }
        return addressDtoList;
    }

    @Override
    public AddressDto getById(long id) {
        AddressDto addressDto = null;
        try {
            addressDto = addressMapper.toDto(addressRepository.findById(id));
        } catch (Exception e) {
            log.error("Error getting a address by id", e);
        }
        return addressDto;
    }

    @Override
    @Transactional
    public AddressDto getLastByUserId(long userId) {
        AddressDto addressDto = null;
        try {
            addressDto = addressMapper.toDto(addressRepository.getLastByUserId(userId));
        } catch (Exception e) {
            log.error("Error getting a last address", e);
        }
        return addressDto;
    }

    @Override
    public List<AddressDto> getAll() {
        List<AddressDto> addressDtoList = null;
        try {
            addressDtoList = addressMapper.toDtoList(addressRepository.findAll());
        } catch (Exception e) {
            log.error("Error getting all address", e);
        }
        return addressDtoList;
    }

    @Override
    @Transactional
    public void updateAddress(AddressDto addressDto) {
        Address address = addressRepository.findById(addressDto.getId());
        address.setCountry(addressDto.getCountry());
        address.setCity(addressDto.getCity());
        address.setStreet(addressDto.getStreet());
        address.setHouseNumber(addressDto.getHouseNumber());
        address.setApartamentNumber(addressDto.getApartamentNumber());
        address.setPostalCode(addressDto.getPostalCode());
        addressRepository.update(address);
    }

    @Override
    @Transactional
    public void addUpdateAddress(AddressDto addressDto, User user) {
        addAddress(addressMapper.toAdditionDto(addressDto), user);
    }

    @Override
    @Transactional
    public void updateSavedAddress(long addressId) {
        Address address = addressRepository.findById(addressId);
        address.setSaved(false);
        addressRepository.update(address);
    }
}
