package com.roshka.dtaporteria.contoller;

import com.roshka.dtaporteria.dto.UserDTO;
import com.roshka.dtaporteria.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    
    @Autowired
    private UserService service;

    @GetMapping("/")
    public String getLogin(Model model) {
        return "login";
    }
    
    @GetMapping("/dashboard")
    public String postLogin(UserDTO user, Model model) {
        System.out.println(user);
        model.addAttribute("user", user);
        return "dashboard";
    }


}
