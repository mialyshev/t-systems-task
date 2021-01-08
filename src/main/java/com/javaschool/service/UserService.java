package com.javaschool.service;

import com.javaschool.dto.user.UserDto;
import com.javaschool.dto.user.UserRegistrationDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto getByEmail(String email);

    UserDto getById(long id);

    UserRegistrationDto registerUser(UserRegistrationDto userRegistrationDto);
}
