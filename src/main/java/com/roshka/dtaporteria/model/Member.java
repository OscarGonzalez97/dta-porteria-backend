package com.roshka.dtaporteria.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String created_by;
    private String ci;
    private String id_member;
    private String is_defaulter;
    private String name;
    private String photo;
    private String surname;
    private String type;
    private String fecha_vencimiento;

    public String getFecha_vencimiento() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(this.fecha_vencimiento, formatter);
            return fecha_vencimiento;
        } catch (Exception e){
            return null;
        }
    }
}
