package com.javaschool.service;

import com.javaschool.entity.Role;
import com.javaschool.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public void addNewRole(String name){
        Role role = new Role();
        role.setName(name);
        roleRepository.add(role);
    }
}
