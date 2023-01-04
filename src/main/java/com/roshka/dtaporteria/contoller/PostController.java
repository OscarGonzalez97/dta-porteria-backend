package com.roshka.dtaporteria.contoller;

import com.roshka.dtaporteria.dto.MemberDTO;
import com.roshka.dtaporteria.service.impl.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private MemberServiceImpl service;

    @GetMapping
    public String hola(){
        return "hola";
    }

    @GetMapping("/list")
    public ResponseEntity list(){
        return new ResponseEntity(service.list(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity agregar(@RequestBody MemberDTO post){
        return new ResponseEntity(service.add(post), HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity edit(@PathVariable(value = "id") String id, @RequestBody MemberDTO post){
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/update")
    public ResponseEntity delete(@PathVariable(value = "id") String id){
        return new ResponseEntity(null, HttpStatus.OK);
    }

}
