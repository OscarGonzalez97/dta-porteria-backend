package com.roshka.dtaporteria.controller;

import com.roshka.dtaporteria.service.MemberService;
import com.roshka.dtaporteria.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Main {

    @Autowired
    RecordService service;
    @Autowired
    MemberService mService;
    @GetMapping
    public String main(Model model) throws Exception {
        int[] meses=service.dataGraficoLinea(2023);
        model.addAttribute("dataLinea",meses);
        model.addAttribute("dataPie",mService.dataGraficoPie());
        model.addAttribute("miembros",mService.list().size());
        model.addAttribute("personas_defaulter",mService.dataTarjetaM());
        model.addAttribute("personas",meses[12]);
        return "dashboard";
    }

}
