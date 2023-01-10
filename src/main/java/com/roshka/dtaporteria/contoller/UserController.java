package com.roshka.dtaporteria.contoller;

import com.roshka.dtaporteria.dto.UserDTO;
import com.roshka.dtaporteria.service.RolService;
import com.roshka.dtaporteria.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private RolService rolService;

    @GetMapping
    public String userCrud(Model model){
        List<UserDTO> users = service.list();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/update/{id}")
    public String getUpdateUser(@PathVariable(value = "id", required = true) String id, Model model){
        UserDTO user = service.getById(id);
        if (user == null){
            return userCrud(model);
        }
        // model.addAttribute("UserDTO", new UserDTO());
        model.addAttribute("user", user);
        model.addAttribute("roles", rolService.list());
        return "updateUser";
    }

    @PostMapping("/update")
    public String postUpdateUser(UserDTO user, Model model){
        service.update(user);
        List<UserDTO> userModel = service.list();
        model.addAttribute("user", userModel);
        return "redirect:/users";
    }

    @GetMapping("/disable/{id}")
    public String disableUser(@PathVariable(value = "id",required = true) String id, Model model){
        service.disable(id);
        List<UserDTO> userModel = service.list();
        model.addAttribute("user", userModel);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable(value = "id",required = true) String id, Model model){
        service.delete(id);
        List<UserDTO> userModel = service.list();
        model.addAttribute("user", userModel);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.getById(id), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity list(){
        return new ResponseEntity(service.list(), HttpStatus.OK);
    }
    @PostMapping("/new")
    public ResponseEntity crear(@RequestBody UserDTO post){
        return new ResponseEntity(service.crear(post), HttpStatus.OK);
    }



}
