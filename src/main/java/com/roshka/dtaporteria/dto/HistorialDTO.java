package com.roshka.dtaporteria.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
public class HistorialDTO {

    private String ci;
    private String historial;
    private String changer;
    private String fechaModificacion;

    public HistorialDTO(String ci, String historial, String changer, String fechaModificacion) {
        this.ci = ci;
        this.historial = historial;
        this.changer = changer;
        this.fechaModificacion = fechaModificacion;
    }
}
