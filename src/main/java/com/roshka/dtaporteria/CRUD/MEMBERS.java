package com.roshka.dtaporteria.CRUD;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MEMBERS {
    private String created_by;
    private Long id_member;
    private String is_defaulter;
    private String photo;
    private String name;
    private String surname;
    private String type;
}
