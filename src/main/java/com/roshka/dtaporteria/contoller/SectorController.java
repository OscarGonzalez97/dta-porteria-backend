package com.roshka.dtaporteria.contoller;

import com.roshka.dtaporteria.dto.SectorDTO;
import com.roshka.dtaporteria.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sectores")
public class SectorController {
    @Autowired
    private SectorService service;

    @GetMapping
    public String Sector(){
        return "hola soy un sector";
    }

    @GetMapping("/{id}")
    public ResponseEntity getSector(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.getById(id), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity list(){
        return new ResponseEntity(service.list(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity agg(@RequestBody SectorDTO post){
        return new ResponseEntity(service.add(post), HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity edit(@PathVariable(value = "id") String id, @RequestBody SectorDTO post){
        return new ResponseEntity(service.edit(id, post), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity delete(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.delete(id), HttpStatus.OK);
    }

}
