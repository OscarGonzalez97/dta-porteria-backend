package com.roshka.dtaporteria.contoller;


import com.roshka.dtaporteria.dto.MemberDTO;
import com.roshka.dtaporteria.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class MemberDatatableController {
    @Autowired
    private MemberService memberService;
    @GetMapping("/Miembros")
    public String Miembros(Model model) {
        List<MemberDTO> miembros = memberService.list();
        model.addAttribute("miembros", miembros);
        return "listmembers";
    }
    @GetMapping("/añadir-miembro")
    public String añadirMiembro(){
        return "formulario-miembro";
    }
}
