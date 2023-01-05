package com.roshka.dtaporteria.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Members {
    private String created_by;
    private Long id_member;
    private String is_defaulter;
    private String photo;
    private String name;
    private String surname;
    private String type;
}
