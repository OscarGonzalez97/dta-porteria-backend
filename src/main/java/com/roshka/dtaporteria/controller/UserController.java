package com.roshka.dtaporteria.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
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
        model.addAttribute("user", user);
        model.addAttribute("roles", rolService.list());
        return "updateUser";
    }

    @PostMapping("/update")
    public String postUpdateUser(UserDTO user, Model model){
        String status = service.update(user);
        List<UserDTO> userModel = service.list();
        model.addAttribute("user", userModel);
        return "redirect:/users" + status;
    }

    @GetMapping("/disable/{id}")
    public String disableUser(@PathVariable(value = "id",required = true) String id, Model model){
        service.disable(id);
        List<UserDTO> userModel = service.list();
        model.addAttribute("user", userModel);
        return "redirect:/users";
    }

    @GetMapping("/new")
    public String getCreateUser(Model model){
        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", rolService.list());
        return "newUser";
    }
    
    @PostMapping("/new")
    public String postCreateUser(UserDTO user, Model model){
        try {
            service.crearAuth(user);
        } catch (FirebaseAuthException e) {
            return "redirect:/users?err500";
        }
        service.crear(user);
        List<UserDTO> userModel = service.list();
        model.addAttribute("user", userModel);
        return "redirect:/users?success1";

    }



    @PostMapping("/delete/{id}")
    public String postDeleteUser(@PathVariable(value = "id",required = true) String id, Model model){

        String status = service.delete(id);

        List<UserDTO> userModel = service.list();
        model.addAttribute("user", userModel);
        return "redirect:/users" + status;
    }
    
    @GetMapping("/delete/{id}")
    public String getDeleteUser(@PathVariable(value = "id",required = true) String id, Model model){
        try {
            service.deleteAuth(id);
        } catch (FirebaseAuthException e) {
            return "redirect:/users?err501";
        }
        service.delete(id);
        List<UserDTO> userModel = service.list();
        model.addAttribute("user", userModel);
        return "redirect:/users?success2";
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.getById(id), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity list(){
        return new ResponseEntity(service.list(), HttpStatus.OK);
    }
    
    // @PostMapping("/new")
    // public ResponseEntity crear(@RequestBody UserDTO post){
    //     return new ResponseEntity(service.crear(post), HttpStatus.OK);
    // }





}
