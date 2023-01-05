package com.roshka.dtaporteria.Model;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @DocumentId
    private String email;
    private String active;
    private String ci;
    private String name;
    private String rol;
    private String surname;
}
