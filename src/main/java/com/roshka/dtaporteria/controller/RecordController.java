package com.roshka.dtaporteria.controller;

import com.roshka.dtaporteria.dto.RecordDTO;
import com.roshka.dtaporteria.reporte.recordExcel;
import com.roshka.dtaporteria.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    @GetMapping("/list")
    public ResponseEntity list(){
        return new ResponseEntity(service.list(), HttpStatus.OK);
    }

    @GetMapping("/ReporteExcel")
    public String ReporteDeExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String Cabecera = "Content-Disposition";
        String Valor = "attachment; filename=Report_"+fechaActual+".xlsx";

        response.setHeader(Cabecera,Valor);

        List<RecordDTO> reporte = service.list();

        recordExcel exporter = new recordExcel(reporte);
        exporter.GenerarReporte(response);
        return "redirect:/records";
    }
}
