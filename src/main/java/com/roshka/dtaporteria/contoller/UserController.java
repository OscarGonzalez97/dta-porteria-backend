package com.roshka.dtaporteria.contoller;

import com.roshka.dtaporteria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.getById(id), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity list(){
        return new ResponseEntity(service.list(), HttpStatus.OK);
    }
}
