package com.roshka.dtaporteria.contoller;

import com.roshka.dtaporteria.dto.SectorDTO;
import com.roshka.dtaporteria.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//@RestController
@Controller
@RequestMapping("/sectores")
public class SectorController {
    @Autowired
    private SectorService service;

    @GetMapping
    public String list(Model modelo){
        modelo.addAttribute("sector",service.list());
        return "listSectores";
    }

    @GetMapping("/addSectores")
    public String aggSectores(Model model){
        model.addAttribute("SectorDTO", new SectorDTO());
        return "addSectores";
    }
    @PostMapping("/add")
    public String mostrarForm(SectorDTO post){
        service.add(post);
        return "redirect:/sectores";
    }
    @GetMapping("/{id}")
    public String formDelete (@PathVariable(value = "id") String id) {
        service.delete(id);
        return "redirect:/sectores";
    }
}
