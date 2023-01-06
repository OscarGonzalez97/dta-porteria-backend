package com.roshka.dtaporteria.dto;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Data;

@Data
public class SectorDTO { //sectores del club
    @DocumentId
    private String id;
    private String sector;
}
//probando si ya soy yo xd
