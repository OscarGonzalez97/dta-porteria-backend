package com.roshka.dtaporteria.controller;

import com.roshka.dtaporteria.dto.SectorDTO;
import com.roshka.dtaporteria.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sectores")
public class SectorController { //controlador de sectores
    @Autowired
    private SectorService service; //variable service para controlar los servicios

    @GetMapping
    public String list(Model modelo){ //metodo para listar todos los sectores
        modelo.addAttribute("sector",service.list());
        modelo.addAttribute("SectorDTO", new SectorDTO());
        return "listSectores";
    }

    @GetMapping("/addSectores")
    public String mostrarForm(Model model){ //metodo que redirecciona al formulario para agregar nuevos sectores
        model.addAttribute("SectorDTO", new SectorDTO());
        return "newSector";
    }
    @PostMapping("/add")
    public String aggSectores(SectorDTO post){ //metodo el cual se encarga de guardar lo cargado en el formulario
        String status = service.add(post);
        return "redirect:/sectores" + status; //redirecciona al listado de sectores para ver que se actualizo
    }
    @GetMapping("/{id}")
    public String formDelete (@PathVariable(value = "id") String id) { //metodo para eliminar un sector
        String status = service.delete(id);
        return "redirect:/sectores" + status; //redirecciona al listado de sectores para ver que se actualizo
    }
}
