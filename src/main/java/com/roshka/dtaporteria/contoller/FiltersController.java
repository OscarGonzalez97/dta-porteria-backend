package com.roshka.dtaporteria.contoller;

import com.roshka.dtaporteria.dto.SectorDTO;
import com.roshka.dtaporteria.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping ("/list")
    public String list(Model modelo){ //metodo para listar todos los records
        modelo.addAttribute("records",service.list());
        return "listRecords";
    }
}
