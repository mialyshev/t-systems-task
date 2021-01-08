package com.javaschool.mapper.user;

import com.javaschool.dto.user.UserDto;
import com.javaschool.dto.user.UserRegistrationDto;
import com.javaschool.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
}