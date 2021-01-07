package com.javaschool.controller;

import com.javaschool.entity.Role;
import com.javaschool.repository.RoleRepository;
import com.javaschool.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author mialyshev
 */

@Controller
@RequiredArgsConstructor
public class HelloController {

    private final RoleService roleService;

    @GetMapping("/hello")
    public String sayHello() {
        return "index";
    }

}