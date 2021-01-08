package com.javaschool.service.impl.user;

import com.javaschool.entity.Role;
import com.javaschool.repository.RoleRepository;
import com.javaschool.service.user.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void addRole(String name){
        Role role = new Role();
        role.setName(name);
        roleRepository.add(role);
    }
}
