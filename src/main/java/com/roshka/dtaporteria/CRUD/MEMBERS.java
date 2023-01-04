package com.roshka.dtaporteria.CRUD;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MEMBERS {
    private String cedula;
    private String created_by;
    private Long id_member;
    private Boolean is_defaulter;
    private String photo;
    private String surname;
    private String type;
}
