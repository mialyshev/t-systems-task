package com.javaschool.controller;

import com.javaschool.dto.product.ProductDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mialyshev
 */

@Controller
@RequiredArgsConstructor
public class HelloController {
    @GetMapping("/")
    public String sayHello(HttpSession session) {
        if (session.getAttribute("bucket") == null) {
            session.setAttribute("bucket", new ArrayList<ProductDto>());
        }
        return "index";
    }

}