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
    private String createdBy;
    private String ci;
    private String idMember;
    private String isDefaulter;
    private String name;
    private String photo;
    private String surname;
    private String type;
    private String fechaVencimiento;

    public Member(String id, String created_by, String ci, String id_member, String is_defaulter, String name, String photo, String surname, String type, String fecha_vencimiento) {
        this.id = id;
        this.createdBy = created_by;
        this.ci = ci;
        this.idMember = id_member;
        this.isDefaulter = is_defaulter;
        this.name = name;
        this.photo = photo;
        this.surname = surname;
        this.type = type;
        this.fechaVencimiento = fecha_vencimiento;
    }
    public String getFecha_vencimiento() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(this.fechaVencimiento, formatter);
            return fechaVencimiento;
        } catch (Exception e){
            return null;
        }
    }
}
