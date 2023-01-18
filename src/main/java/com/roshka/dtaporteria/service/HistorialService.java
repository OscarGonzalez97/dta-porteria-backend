package com.roshka.dtaporteria.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.roshka.dtaporteria.config.UserRecordCustom;
import com.roshka.dtaporteria.dto.HistorialDTO;
import com.roshka.dtaporteria.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;


/*
**hay que anular la capacidad de editar el ci de un miembro
 */
@Service
public class HistorialService {


    @Autowired
    Firestore firestore;
    public Boolean addHistorial(HistorialDTO historialDTO) {
        UserRecordCustom user = (UserRecordCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        historialDTO.setChanger(user.getPassword());
        Map<String, Object> docData = getDocData(historialDTO);
        DocumentReference posts = getCollection().document(historialDTO.getCi());
        ApiFuture<DocumentSnapshot> future = posts.get();


        try {
            DocumentSnapshot document = future.get();
            if (!document.exists()){
                Map<String, Object> inicializar = new HashMap<>();
                inicializar.put("lista","");
                ApiFuture<WriteResult> futureCrear = posts.set(inicializar);
                futureCrear.get();
            }
            ApiFuture<WriteResult> futureAgregar = posts.update("lista", FieldValue.arrayUnion(docData));
            futureAgregar.get();
           return Boolean.TRUE;
        } catch (InterruptedException | ExecutionException e) {
            return Boolean.FALSE;
        }
    }

    public List<Map<String, Object>> list(String id){
        DocumentReference posts = getCollection().document(id);
        ApiFuture<DocumentSnapshot> ResultApiFuture = posts.get();
        List<Map<String,Object>> lista= null;
        try {
            DocumentSnapshot documento = ResultApiFuture.get();
            lista = (List<Map<String, Object>>) documento.get("lista");

            return lista;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    private static Map<String, Object> getDocData(HistorialDTO post) {
        Map<String, Object> campos = new HashMap<>();
        campos.put("ci" , post.getCi());
        campos.put("historial", post.getHistorial());
        campos.put("changer",post.getChanger());
        campos.put("fechaModificacion",post.getFechaModificacion());
        return campos;
    }


    private CollectionReference getCollection() {
        return firestore.collection("HISTORIAL");
    }

}
