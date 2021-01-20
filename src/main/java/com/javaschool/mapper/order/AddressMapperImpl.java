package com.javaschool.mapper.order;

import com.javaschool.dto.order.AddressAdditionDto;
import com.javaschool.dto.order.AddressDto;
import com.javaschool.entity.Address;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddressMapperImpl {

    public AddressDto toDto(Address address){
        if (address == null) {
            return null;
        }
        AddressDto.AddressDtoBuilder addressDto = AddressDto.builder();

        addressDto.id(address.getId());
        addressDto.country(address.getCountry());
        addressDto.city(address.getCity());
        addressDto.postalCode(address.getPostalCode());
        addressDto.street(address.getStreet());
        addressDto.houseNumber(address.getHouseNumber());
        addressDto.apartamentNumber(address.getApartamentNumber());

        return addressDto.build();
    }

    public List<AddressDto> toDtoList(List<Address> addressList) {
        if (addressList == null) {
            return null;
        }

        List<AddressDto> list = new ArrayList<AddressDto>(addressList.size());
        for (Address address : addressList) {
            list.add(toDto(address));
        }

        return list;
    }

    public AddressAdditionDto toAdditionDto(AddressDto addressDto){
        if (addressDto == null) {
            return null;
        }
        AddressAdditionDto.AddressAdditionDtoBuilder additionDtoBuilder = AddressAdditionDto.builder();

        additionDtoBuilder.country(addressDto.getCountry());
        additionDtoBuilder.city(addressDto.getCity());
        additionDtoBuilder.postalCode(addressDto.getPostalCode());
        additionDtoBuilder.street(addressDto.getStreet());
        additionDtoBuilder.houseNumber(addressDto.getHouseNumber());
        additionDtoBuilder.apartamentNumber(addressDto.getApartamentNumber());
        additionDtoBuilder.isSaved(true);

        return additionDtoBuilder.build();
    }
}
