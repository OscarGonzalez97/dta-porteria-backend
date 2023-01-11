package com.roshka.dtaporteria.dto;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Data;

@Data
public class TypeDTO { //como estara en firebase
    @DocumentId
    private String Type;

}
