package com.javaschool.service.impl;

import com.javaschool.dto.user.UserDto;
import com.javaschool.dto.user.UserRegistrationDto;
import com.javaschool.entity.User;

import com.javaschool.mapper.UserMapperImpl;
import com.javaschool.repository.RoleRepository;
import com.javaschool.repository.UserRepository;
import com.javaschool.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapperImpl userMapper;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<UserDto> getAll() {
        List<UserDto> userDtoList = null;
        try {
            userDtoList = userMapper.toDtoList(userRepository.findAll());
        } catch (Exception e) {
            log.error("Error getting all the users", e);
        }
        return userDtoList;
    }

    @Override
    @Transactional
    public UserDto getByEmail(String email) {
        UserDto userDto = null;
        try {
            userDto = userMapper.toDto(userRepository.findByEmail(email));
        } catch (Exception e) {
            log.error("Error getting a user by email", e);
        }
        return userDto;
    }

    @Override
    public UserDto getById(long id) {
        UserDto userDto = null;
        try {
            userDto = userMapper.toDto(userRepository.findById(id));
        } catch (Exception e) {
            log.error("Error getting a user by id", e);
        }
        return userDto;
    }

    @Override
    @Transactional
    public UserRegistrationDto registerUser(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setDob(getDate(userRegistrationDto.getDob()));
        user.setEmail(userRegistrationDto.getEmail());
        user.setRoleSet(Collections.singleton(roleRepository.findByName("ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(userRegistrationDto.getPassword()));
        userRepository.save(user);
        return userRegistrationDto;
    }

    private LocalDate getDate(String date){
        String[] nums = date.split("-");
        return LocalDate.of(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]), Integer.parseInt(nums[2]));
    }
}
