package com.roshka.dtaporteria.controller;

import com.roshka.dtaporteria.dto.RecordDTO;
import com.roshka.dtaporteria.model.Records;
import com.roshka.dtaporteria.reporte.recordExcel;
import com.roshka.dtaporteria.repository.RecordsRepository;
import com.roshka.dtaporteria.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/records")
public class RecordController {
    @Autowired
    private RecordService service;
    @Autowired
    private RecordsRepository recordsRepository;

    @GetMapping
    public String list(Model modelo){ //metodo para listar todos los records
        modelo.addAttribute("records", recordsRepository.findAll());
        return "listRecords";
    }

    @GetMapping("/ver/{id}")
    public String verDetalles (@PathVariable(value = "id") String id, Model modelo) { //metodo para mostrar los detalles de cada record
        Optional<Records> verificacion = recordsRepository.findById(id);
        modelo.addAttribute("records", verificacion.isPresent()?verificacion.get(): null);
        return "ver";
    }

    @GetMapping("/ReporteExcel")
    public String ReporteDeExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String Cabecera = "Content-Disposition";
        String Valor = "attachment; filename=Reporte_"+fechaActual+".xlsx";

        response.setHeader(Cabecera,Valor);

        List<RecordDTO> reporte = service.list();

        recordExcel exporter = new recordExcel(reporte);
        exporter.GenerarReporte(response);
        return "redirect:/records";
    }
}
