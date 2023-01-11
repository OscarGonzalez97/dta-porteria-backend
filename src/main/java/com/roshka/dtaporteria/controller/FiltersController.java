package com.roshka.dtaporteria.controller;

import com.roshka.dtaporteria.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/filters")
public class FiltersController {
    @Autowired
    private RecordService service;

    @GetMapping
    public String mostrarFiltros () {
        return "filters";
    }
    @PostMapping("/add")
    public String addFiltros(){
        System.out.println("Funciona");

        return "redirect:/filters";
    }

    
}
