package com.roshka.dtaporteria.dto;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    private String fecha_vencimiento;

    public String getFecha_vencimiento() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate ld = LocalDate.parse(this.fecha_vencimiento, formatter);
            return fecha_vencimiento;
        } catch (Exception e){
            return null;
        }
    }

}
