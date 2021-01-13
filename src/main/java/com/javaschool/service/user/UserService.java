package com.javaschool.service.user;

import com.javaschool.dto.user.UserDto;
import com.javaschool.dto.user.UserRegistrationDto;
import com.javaschool.dto.user.UserUpdateInfoDto;
import com.javaschool.dto.user.UserUpdatePassDto;
import com.javaschool.entity.User;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto getDtoByEmail(String email);

    User getByEmail(String email);

    UserDto getById(long id);

    UserDto toDto(User user);

    void registerUser(UserRegistrationDto userRegistrationDto);

    UserUpdateInfoDto getUserUpdateDto(UserDto userDto);

    void updateUserInfo(UserUpdateInfoDto userUpdateDto);

    UserUpdatePassDto getUserUpdatePass(UserDto userDto);

    boolean updateUserPass(UserUpdatePassDto userUpdatePassDto);

    boolean checkMatch(UserUpdatePassDto userUpdatePassDto, String password);
}
