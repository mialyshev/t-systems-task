package com.javaschool.mapper.user;

import com.javaschool.dto.user.UserDto;
import com.javaschool.dto.user.UserUpdateInfoDto;
import com.javaschool.dto.user.UserUpdatePassDto;
import com.javaschool.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapperImpl {

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.id(user.getId());
        userDto.firstName(user.getFirstName());
        userDto.lastName(user.getLastName());
        userDto.dob(user.getDob());
        userDto.email(user.getEmail());

        return userDto.build();
    }

    public List<UserDto> toDtoList(List<User> userList) {
        if (userList == null) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>(userList.size());
        for (User user : userList) {
            list.add(toDto(user));
        }

        return list;
    }

    public UserUpdateInfoDto toUpdateInfoDto(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        UserUpdateInfoDto.UserUpdateInfoDtoBuilder userDtoBuilder = UserUpdateInfoDto.builder();

        userDtoBuilder.id(userDto.getId());
        userDtoBuilder.firstName(userDto.getFirstName());
        userDtoBuilder.lastName(userDto.getLastName());
        userDtoBuilder.dob(userDto.getDob().toString());
        userDtoBuilder.email(userDto.getEmail());
        userDtoBuilder.confirmEmail(userDto.getEmail());
        return userDtoBuilder.build();
    }

    public UserUpdatePassDto toUpdatePassDto(UserDto userDto){
        if (userDto == null) {
            return null;
        }

        UserUpdatePassDto.UserUpdatePassDtoBuilder userUpdatePassDtoBuilder = UserUpdatePassDto.builder();

        userUpdatePassDtoBuilder.id(userDto.getId());
        return userUpdatePassDtoBuilder.build();
    }
}