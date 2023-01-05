package com.roshka.dtaporteria.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class USERS {
    private String active;
    private String ci;
    private String name;
    private String rol;
    private String surname;
}
