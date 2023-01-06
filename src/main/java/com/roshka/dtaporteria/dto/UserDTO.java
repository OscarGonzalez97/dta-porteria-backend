package com.roshka.dtaporteria.dto;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Data;

@Data
public class UserDTO { //usuarios de la app
    @DocumentId
    private String id;
    private String active;
    private String ci;
    private String name;
    private String surname;
    private String rol;
}
