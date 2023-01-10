package com.roshka.dtaporteria.contoller;



import com.roshka.dtaporteria.dto.TypeDTO;

import com.roshka.dtaporteria.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//@RestController
@Controller
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private TypeService service;
    @GetMapping("/{id}")
    public ResponseEntity getSector(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.getById(id), HttpStatus.OK);
    }

    @GetMapping("/prueba")
    public String Listtype(Model model){
        model.addAttribute("type",service.list());
        return "pruebaType";
    }

    @GetMapping("/list")
    public ResponseEntity list(){
        return new ResponseEntity(service.list(), HttpStatus.OK);
    }



    @PostMapping("/add")
    public ResponseEntity agg(@RequestBody TypeDTO post){
        return new ResponseEntity(service.add(post), HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity edit(@PathVariable(value = "id") String id, @RequestBody TypeDTO post){
        return new ResponseEntity(service.edit(id, post), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity delete(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.delete(id), HttpStatus.OK);
    }
}
