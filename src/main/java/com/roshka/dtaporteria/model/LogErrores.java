package com.roshka.dtaporteria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class LogErrores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fecha;
    private String mensaje;

    public LogErrores(String fecha, String mensaje) {
        this.fecha = fecha;
        this.mensaje = mensaje;
    }
}
