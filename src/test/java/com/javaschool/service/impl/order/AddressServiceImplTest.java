package com.javaschool.service.impl.order;

import com.javaschool.dto.order.AddressAdditionDto;
import com.javaschool.dto.order.AddressDto;
import com.javaschool.entity.Address;
import com.javaschool.exception.UserException;
import com.javaschool.mapper.order.AddressMapperImpl;
import com.javaschool.repository.order.AddressRepository;
import com.javaschool.repository.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapperImpl addressMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    AddressServiceImpl addressService;

    private Address getAddressEntity() {
        return Address.builder()
                .id(1)
                .country("test")
                .city("test")
                .street("test")
                .postalCode("111111")
                .houseNumber("1")
                .apartamentNumber("1")
                .isSaved(true)
                .build();
    }

    private AddressDto getAddressDto() {
        return AddressDto.builder()
                .id(1)
                .country("test")
                .city("test")
                .street("test")
                .postalCode("111111")
                .houseNumber("1")
                .apartamentNumber("1")
                .build();
    }

    private AddressAdditionDto getAddressAdditionDto() {
        return AddressAdditionDto.builder()
                .country("test")
                .city("test")
                .street("test")
                .postalCode("111111")
                .houseNumber("1")
                .apartamentNumber("1")
                .isSaved(true)
                .build();
    }

    @Test
    public void getByIdTest() throws UserException {
        lenient().when(addressRepository.findById(anyInt())).thenReturn(getAddressEntity());
        when(addressMapper.toDto(any())).thenReturn(getAddressDto());
        AddressDto result = addressService.getById(1);
        assertEquals(getAddressDto(), result);
    }

    @Test
    public void getAllSavedTest() throws UserException {
        lenient().when(addressRepository.findAllSavedByUserId(anyInt())).thenReturn(Collections.singletonList(getAddressEntity()));
        when(addressMapper.toDtoList(any())).thenReturn(Collections.singletonList(getAddressDto()));
        List<AddressDto> result = addressService.getAllSaved(1);
        assertEquals(1, result.size());
        AddressDto dto = result.get(0);
        assertEquals(getAddressDto(), dto);
    }

    @Test
    public void addAddressTest() {
        addressService.addAddress(getAddressAdditionDto(), null);
        verify(addressRepository, only()).save(any(Address.class));
    }

    @Test
    public void updateAddressTest() throws UserException {
        lenient().when(addressRepository.findById(anyInt())).thenReturn(getAddressEntity());
        addressService.updateAddress(getAddressDto());
        verify(addressRepository, only()).update(any(Address.class));
    }

}
