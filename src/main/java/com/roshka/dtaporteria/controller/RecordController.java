package com.roshka.dtaporteria.controller;

import com.roshka.dtaporteria.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/records")
public class RecordController {
    @Autowired
    private RecordService service;

    @GetMapping
    public String list(Model modelo){ //metodo para listar todos los records
        modelo.addAttribute("records", service.list());
        return "listRecords";
    }

    @GetMapping("/ver/{id}")
    public String verDetalles (@PathVariable(value = "id") String id, Model modelo) { //metodo para mostrar los detalles de cada record
        modelo.addAttribute("records", service.getById(id));
        return "ver";
    }
    @GetMapping("/list")
    public ResponseEntity list(){
        return new ResponseEntity(service.list(), HttpStatus.OK);
    }

}
