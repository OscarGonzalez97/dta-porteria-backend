package com.roshka.dtaporteria.contoller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class MemberDatatableController {
    @GetMapping("/Miembros")
    public String Miembros() {
        return "listmembers";
    }
}
