package com.javaschool.service.order;

import com.javaschool.dto.order.AddressAdditionDto;
import com.javaschool.dto.order.AddressDto;
import com.javaschool.entity.User;

import java.util.List;

public interface AddressService {

    void addAddress(AddressAdditionDto additionDto, User user);

    List<AddressDto> getAllSaved(long id);

    AddressDto getById(long id);

    AddressDto getLastByUserId(long userId);

    List<AddressDto> getAll();
}
