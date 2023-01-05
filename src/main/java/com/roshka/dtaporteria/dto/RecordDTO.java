package com.roshka.dtaporteria.dto;

import lombok.Data;

@Data
public class RecordDTO {
    private String id;
    private String ci_member;
    private String ci_portero;
    private String date_time;
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
}
