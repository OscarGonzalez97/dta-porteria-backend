package com.roshka.dtaporteria.dto;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    @DocumentId
    private String id;
    private String created_by;
    private Integer id_member;
    private String is_defaulter;
    private String name;
    private String photo;
    private String surname;
    private String type;
    private LocalDate fecha_vencimiento;

    public String getFecha_vencimiento(){
        return fecha_vencimiento.toString();
    }
}
