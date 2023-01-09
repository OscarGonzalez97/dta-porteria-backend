package com.roshka.dtaporteria.contoller;

import com.roshka.dtaporteria.dto.UserDTO;
import com.roshka.dtaporteria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
<<<<<<< HEAD
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
=======
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
>>>>>>> develop

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

<<<<<<< HEAD
    @GetMapping
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model){
        model.addAttribute("name", name);
        return "greeting";
=======
    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.getById(id), HttpStatus.OK);
>>>>>>> develop
    }

    @GetMapping("/list")
    public ResponseEntity list(){
        return new ResponseEntity(service.list(), HttpStatus.OK);
    }
    @PostMapping("/new")
    public ResponseEntity crear(@RequestParam UserDTO post){
        return new ResponseEntity(service.crear(post), HttpStatus.OK);
    }



}
