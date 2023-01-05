package com.roshka.dtaporteria.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RECORDS {
    private String ci_member;
    private Long ci_portero;
    private Long date_time;
    private String email_portero;
    private Long id_member;
    private Boolean is_defaulter;
    private Boolean is_exit;
    private Boolean is_walk;
    private String name_member;
    private String name_portero;
    private String photo;
    private String surname_member;
    private String surname_poretero;
    private String type;
}
