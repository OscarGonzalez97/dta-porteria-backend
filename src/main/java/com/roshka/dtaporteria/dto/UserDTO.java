package com.roshka.dtaporteria.dto;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;

@Data
public class UserDTO {
    @DocumentId
    private String id;
    private String active;
    private String ci;
    private String name;
    private String surname;
    private String rol;
}