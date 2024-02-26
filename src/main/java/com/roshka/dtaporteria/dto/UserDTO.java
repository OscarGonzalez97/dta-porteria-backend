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
public class UserDTO {
    @DocumentId
    private String id;
    private String active;
    private String ci;
    private String name;
    private String surname;
    private String rol;
}
