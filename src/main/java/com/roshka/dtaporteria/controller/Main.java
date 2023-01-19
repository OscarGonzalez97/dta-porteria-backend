package com.roshka.dtaporteria.controller;

import com.roshka.dtaporteria.model.Sync;
import com.roshka.dtaporteria.repository.MembersRepository;
import com.roshka.dtaporteria.repository.SyncRepository;
import com.roshka.dtaporteria.service.MemberService;
import com.roshka.dtaporteria.service.RecordService;
import com.roshka.dtaporteria.service.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class Main {

    @Autowired
    RecordService service;
    @Autowired
    MemberService mService;
    @Autowired
    MembersRepository membersRepository;
    @Autowired
    private SyncRepository syncRepository;
    @Autowired
    Scheduler scheduler;
    @GetMapping
    public String main(Model model) throws Exception {
        int[] meses=service.dataGraficoLinea(2023);    //obteniendo los datos
        model.addAttribute("dataLinea",meses);
        model.addAttribute("dataPie",mService.dataGraficoPie());
        model.addAttribute("miembros",membersRepository.findAll().size());
        model.addAttribute("morosos", membersRepository.countAllByIsDefaulterIsNotNull());
        model.addAttribute("personas",meses[12]);  //le asigno el ultimo valor del array pq ahi esta la cantidad de persona que entraron ese dia (codigo en RecordService)
        return "dashboard";
    }
    @GetMapping("/sync")
    public String getSync(){
        syncRepository.updateCantidad();
        List<Sync> sync = syncRepository.findAll();
        if (!sync.isEmpty() && sync.get(0).getCantidad() <=3){
            scheduler.syncMembers();
            scheduler.checkFechaAndSync();
            scheduler.syncRecords();
        }
        return "redirect:/";
    }
}
