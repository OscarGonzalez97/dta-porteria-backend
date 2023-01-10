package com.roshka.dtaporteria.dto;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class SectorDTO { //sectores del club
    @DocumentId
    private String id;
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private String id;

}