package com.roshka.dtaporteria.dto;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Data;

@Data
public class MemberDTO { //miembros del club
    @DocumentId
    private String id;
    private String created_by;
    private Integer id_member;
    private String is_defaulter;
    private String name;
    private String photo;
    private String surname;
    private String type;

}
