package com.roshka.dtaporteria.controller;

import com.roshka.dtaporteria.dto.UserDTO;
import com.roshka.dtaporteria.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    
    @Autowired
    private UserService service;

    @GetMapping
    public String getLogin(Model model) {
        model.addAttribute("user", new UserDTO());
        return "login";
    }
    
    @PostMapping
    public String postLogin(UserDTO user, Model model) {
        System.out.println(user);
        model.addAttribute("user", user);
        return "dashboard";
    }

}
