package com.roshka.dtaporteria.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.roshka.dtaporteria.dto.RecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FiltersService {

    List<String> tipos = new ArrayList<>();;
    @Autowired
    private RecordService service;
    /*public List<String> filterType (String type) {
        if (service.getById(type).getType() == ) {

        }
        tipos.add(service.getById(type).getType());
        return tipos;

    }*/
    public void filterIdMember () {

    }
    public void filterFecha () {

    }
}
