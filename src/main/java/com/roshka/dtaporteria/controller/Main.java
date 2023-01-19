package com.roshka.dtaporteria.controller;

import com.roshka.dtaporteria.repository.MembersRepository;
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
    @Autowired
    MembersRepository membersRepository;
    @GetMapping
    public String main(Model model) throws Exception {
        model.addAttribute("dataLinea",service.dataGraficoLinea(2023));
        model.addAttribute("dataPie",mService.dataGraficoPie());
//        model.addAttribute("chartData",mservice.listadelista());
        model.addAttribute("miembros",membersRepository.findAll().size());
        model.addAttribute("morosos", membersRepository.countAllByIsDefaulterIsNotNull());
//        model.addAttribute("personas",service.dataGrafico(2024)); //falta
        return "dashboard";
    }

}
