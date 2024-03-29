package com.roshka.dtaporteria.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.auto.value.AutoValue.Builder;
import com.google.cloud.firestore.annotation.DocumentId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    @DocumentId
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
    public void setIs_defaulter(Object object){
        if (object == null)
        {
            this.is_defaulter = null;
            return;
        }
        this.is_defaulter = object.toString();
    }


    @Override
    public String toString() {
        return "MemberDTO{" +
                "id='" + id + '\'' +
                ", created_by='" + created_by + '\'' +
                ", ci='" + ci + '\'' +
                ", id_member='" + id_member + '\'' +
                ", is_defaulter='" + is_defaulter + '\'' +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", surname='" + surname + '\'' +
                ", type='" + type + '\'' +
                ", fecha_vencimiento='" + fecha_vencimiento + '\'' +
                '}';
    }
}
