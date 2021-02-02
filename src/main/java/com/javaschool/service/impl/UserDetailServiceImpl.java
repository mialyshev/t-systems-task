package com.javaschool.service.impl;

import com.javaschool.entity.Role;
import com.javaschool.entity.User;
import com.javaschool.exception.UserException;
import com.javaschool.repository.user.RoleRepository;
import com.javaschool.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User client = null;
        try {
            client = userRepository.findByEmail(email);
        }catch (UserException e){
            log.error("Error getting user by email");
        }

        if (client == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }


        return new org.springframework.security.core.userdetails.User(
                client.getEmail(), client.getPassword(), mapRolesToAuthorities(email));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String email) {
        Collection<Role> roleEntityCollection = new ArrayList<>();
        try {
            roleEntityCollection = roleRepository.findRolesByUserEmail(email);
        } catch (UserException e) {
            log.error("Error getting role for user");
        }catch (Exception e) {
            log.error("Error at UserDetailService.mapRolesToAuthorities()");
        }
        return roleEntityCollection.stream()
                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName()))
                .collect(Collectors.toList());
    }
}
