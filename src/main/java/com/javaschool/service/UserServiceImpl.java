package com.javaschool.service;

import com.javaschool.entity.Role;
import com.javaschool.entity.User;
import com.javaschool.repository.RoleRepository;
import com.javaschool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User client = userRepository.findByEmail(email);

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roleEntityCollection.stream()
                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName()))
                .collect(Collectors.toList());
    }
}
