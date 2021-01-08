package com.javaschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author mialyshev
 */

@Controller
@RequiredArgsConstructor
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "index";
    }

}