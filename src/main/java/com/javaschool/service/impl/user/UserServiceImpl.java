package com.javaschool.service.impl.user;

import com.javaschool.dto.user.UserDto;
import com.javaschool.dto.user.UserRegistrationDto;
import com.javaschool.dto.user.UserUpdateInfoDto;
import com.javaschool.dto.user.UserUpdatePassDto;
import com.javaschool.entity.User;
import com.javaschool.exception.UserException;
import com.javaschool.mapper.user.UserMapperImpl;
import com.javaschool.repository.user.RoleRepository;
import com.javaschool.repository.user.UserRepository;
import com.javaschool.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collection;
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
        } catch (UserException e) {
            log.error("Error getting all the users", e);
        }catch (Exception e){
            log.error("Error at UserService.getAll()", e);
        }
        return userDtoList;
    }

    @Override
    @Transactional
    public UserDto getDtoByEmail(String email) {
        UserDto userDto = null;
        try {
            userDto = userMapper.toDto(userRepository.findByEmail(email));
        } catch (UserException e) {
            log.error("Error getting a user by email", e);
        }catch (Exception e){
            log.error("Error at UserService.getDtoByEmail()", e);
        }
        return userDto;
    }

    @Override
    @Transactional
    public User getByEmail(String email){
        User user = null;
        try {
            user = userRepository.findByEmail(email);
        } catch (UserException e) {
            log.error("Error getting a user by email", e);
        }catch (Exception e){
            log.error("Error at UserService.getByEmail()", e);
        }
        return user;
    }

    @Override
    public UserDto getById(long id) {
        UserDto userDto = null;
        try {
            userDto = userMapper.toDto(userRepository.findById(id));
        } catch (UserException e) {
            log.error("Error getting a user by id", e);
        }catch (Exception e){
            log.error("Error at UserService.getById()", e);
        }
        return userDto;
    }

    @Override
    public User getUserById(long id) {
        User user = null;
        try {
            user = userRepository.findById(id);
        } catch (UserException e) {
            log.error("Error getting a user by id", e);
        }catch (Exception e){
            log.error("Error at UserService.getUserById()", e);
        }
        return user;
    }

    @Override
    public UserDto toDto(User user) {
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public void registerUser(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        try {
            user.setFirstName(userRegistrationDto.getFirstName());
            user.setLastName(userRegistrationDto.getLastName());
            user.setDob(getDate(userRegistrationDto.getDob()));
            user.setEmail(userRegistrationDto.getEmail());
            user.setRoleSet(Collections.singleton(roleRepository.findByName("ROLE_USER")));
            user.setPassword(bCryptPasswordEncoder.encode(userRegistrationDto.getPassword()));
            userRepository.save(user);
        }catch (UserException e){
            log.error("Error while register user", e);
        }catch (Exception e){
            log.error("Error at UserService.registerUser()");
        }
    }

    @Override
    public UserUpdateInfoDto getUserUpdateDto(UserDto userDto) {
        return userMapper.toUpdateInfoDto(userDto);
    }



    private LocalDate getDate(String date){
        String[] nums = date.split("-");
        return LocalDate.of(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]), Integer.parseInt(nums[2]));
    }

    @Override
    @Transactional
    public void updateUserInfo(UserUpdateInfoDto userUpdateDto){
        try {
            User user = userRepository.findById(userUpdateDto.getId());
            user.setFirstName(userUpdateDto.getFirstName());
            user.setLastName(userUpdateDto.getLastName());
            user.setDob(getDate(userUpdateDto.getDob()));
            user.setEmail(userUpdateDto.getEmail());
            Collection<SimpleGrantedAuthority> nowAuthorities =(Collection<SimpleGrantedAuthority>)SecurityContextHolder
                    .getContext().getAuthentication().getAuthorities();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
                    user.getPassword(),
                    nowAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            userRepository.update(user);
        }catch (UserException e) {
            log.error("Error updating user info", e);
        }catch (Exception e){
            log.error("Error at UserService.updateUserInfo()", e);
        }
    }

    @Override
    public UserUpdatePassDto getUserUpdatePass(UserDto userDto) {
        return userMapper.toUpdatePassDto(userDto);
    }

    @Override
    @Transactional
    public boolean updateUserPass(UserUpdatePassDto userUpdatePassDto) {
        try {
            User user = userRepository.findById(userUpdatePassDto.getId());
            if(userUpdatePassDto.getPassword().equals(userUpdatePassDto.getConfirmPassword())){
                user.setPassword(bCryptPasswordEncoder.encode(userUpdatePassDto.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(null);
                userRepository.update(user);
                return true;
            }
        }catch (UserException e) {
            log.error("Error updating user pass", e);
        }catch (Exception e){
            log.error("Error at UserService.updateUserPass()", e);
        }
        return false;
    }

    @Override
    public boolean checkMatch(UserUpdatePassDto userUpdatePassDto, String password){
        if(bCryptPasswordEncoder.matches(userUpdatePassDto.getOldPassword(), password)){
            return true;
        }
        return false;
    }
}
