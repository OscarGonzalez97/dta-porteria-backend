package com.roshka.dtaporteria.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NoArgsConstructor
public class Member{

    public static final String STATUS_ACTIVE = "A";
    public static final String STATUS_INACTIVE = "I";

    public static final String TYPE_SOCIO = "socio";

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
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
    private String status;

    public Member(String id, String createdBy, String ci, String idMember, String isDefaulter, String name, String photo, String surname, String type, String fechaVencimiento) {
        this.id = id;
        this.createdBy = createdBy;
        this.ci = ci;
        this.idMember = idMember;
        this.isDefaulter = isDefaulter;
        this.name = name;
        this.photo = photo;
        this.surname = surname;
        this.type = type;
        this.fechaVencimiento = fechaVencimiento;
    }
    public String getFechaVencimiento() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(this.fechaVencimiento, formatter);
            return fechaVencimiento;
        } catch (Exception e){
            return null;
        }
    }
}
