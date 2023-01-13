package com.roshka.dtaporteria.controller;

import com.roshka.dtaporteria.dto.UserDTO;
import com.roshka.dtaporteria.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    
    @Autowired
    private UserService service;


    @GetMapping("/error")
    public String base() {
        return "holaMundo";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

}
