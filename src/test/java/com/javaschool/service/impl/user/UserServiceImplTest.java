package com.javaschool.service.impl.user;

import com.javaschool.dto.user.UserDto;
import com.javaschool.dto.user.UserRegistrationDto;
import com.javaschool.entity.Role;
import com.javaschool.entity.User;
import com.javaschool.exception.UserException;
import com.javaschool.mapper.user.UserMapperImpl;
import com.javaschool.repository.user.RoleRepository;
import com.javaschool.repository.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapperImpl userMapper;

    @Mock
    private PasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User getUserEntity() {
        return User.builder()
                .id(1)
                .firstName("test")
                .lastName("test")
                .dob(LocalDate.of(2000, 1, 1))
                .email("email")
                .password("pass")
                .build();
    }

    private UserDto getUserDto() {
        return UserDto.builder()
                .id(1)
                .firstName("test")
                .lastName("test")
                .dob(LocalDate.of(2000, 1, 1))
                .email("email")
                .build();
    }

    private Role getRoleEntity() {
        return Role.builder()
                .id(1)
                .name("test")
                .build();
    }

    private UserRegistrationDto getUserRegistrationDto() {
        return UserRegistrationDto.builder()
                .firstName("test")
                .lastName("test")
                .dob("2000-01-01")
                .email("email")
                .confirmEmail("email")
                .password("pass")
                .confirmPassword("pass")
                .build();
    }


    @Test
    public void getAllTest() throws UserException {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(getUserEntity()));
        when(userMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(getUserDto()));
        List<UserDto> result = userService.getAll();
        assertEquals(1, result.size());
        UserDto dto = result.get(0);
        assertEquals(getUserDto(), dto);
    }

    @Test
    public void getByIdTest() throws UserException {
        lenient().when(userRepository.findById(anyInt())).thenReturn(getUserEntity());
        when(userMapper.toDto(any())).thenReturn(getUserDto());
        UserDto result = userService.getById(1);
        assertEquals(getUserDto(), result);
    }

    @Test
    public void getByEmailTest() throws UserException {
        when(userRepository.findByEmail(anyString())).thenReturn(getUserEntity());
        when(userMapper.toDto(any())).thenReturn(getUserDto());
        User result = userService.getByEmail("test");
        assertEquals(getUserEntity(), result);
    }

    @Test
    public void registerUserTest() throws UserException {
        when(roleRepository.findByName(anyString())).thenReturn(getRoleEntity());
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encode");
        userService.registerUser(getUserRegistrationDto());
        verify(userRepository, only()).save(any(User.class));
    }
}
