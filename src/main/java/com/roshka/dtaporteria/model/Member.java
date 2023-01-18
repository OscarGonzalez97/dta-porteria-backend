package com.roshka.dtaporteria.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NoArgsConstructor
public class Member{
    @Id
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

    public Member(String id, String created_by, String ci, String id_member, String is_defaulter, String name, String photo, String surname, String type, String fecha_vencimiento) {
        this.id = id;
        this.created_by = created_by;
        this.ci = ci;
        this.id_member = id_member;
        this.is_defaulter = is_defaulter;
        this.name = name;
        this.photo = photo;
        this.surname = surname;
        this.type = type;
        this.fecha_vencimiento = fecha_vencimiento;
    }
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
