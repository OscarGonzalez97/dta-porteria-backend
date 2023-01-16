package com.roshka.dtaporteria.dto;

import com.google.auto.value.AutoValue.Builder;
import com.google.cloud.firestore.annotation.DocumentId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordDTO { //para el registro de entradas y salidas
    @DocumentId
    private String id;
    private String ci_member;
    private String ci_portero;
    private String date_time; //fecha y hora exacta
    private String email_portero;
    private String id_member;
    private String is_defaulter;
    private Boolean is_exit;
    private Boolean is_walk;
    private String name_member;
    private String name_portero;
    private String photo;
    private String surname_member;
    private String surname_portero;
    private String type;
    private String sector;
}
