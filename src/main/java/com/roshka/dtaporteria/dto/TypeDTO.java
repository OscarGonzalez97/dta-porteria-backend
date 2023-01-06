package com.roshka.dtaporteria.dto;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Data;

@Data
public class TypeDTO { //como estara en firebase
    @DocumentId
    private String Type;

/*
    tipos de miembros

    Invitado = "Invitado";
    }Guarderia = "Guarderia";
    Gimnasio = "Gimnasio";
    Eventos = "Eventos";
    Staff = "Staff";
    Socio = "Socio";
    Restaurante = "Restaurante";
    private static String Tenis = "Tenis";
    private static String Piscina = "Piscina";*/
}
