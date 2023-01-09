package com.roshka.dtaporteria.contoller;

import com.roshka.dtaporteria.dto.UserDTO;
import com.roshka.dtaporteria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model){
        model.addAttribute("name", name);
        return "greeting";
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
