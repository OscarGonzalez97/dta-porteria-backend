package com.roshka.dtaporteria.dto;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Data;


@Data
public class SectorDTO { //sectores del club
    @DocumentId
    private String id ; //el unico campo es el id que al mismo tiempo es un sector
    

}