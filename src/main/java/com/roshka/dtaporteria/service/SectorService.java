package com.roshka.dtaporteria.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.roshka.dtaporteria.config.FirebaseInitializer;
import com.roshka.dtaporteria.dto.SectorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class SectorService {
    @Autowired
    private FirebaseInitializer firebase;

    public Map<String, Object> getById(String id) { //metodo para obtener el id
        DocumentReference docRef = getCollection().document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            return document.getData();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public List<SectorDTO> list(){ //metodo para generar el listado de sectores
        List<SectorDTO> response = new ArrayList<>();
        SectorDTO post;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                post = doc.toObject(SectorDTO.class);
                response.add(post);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }
    public String add(SectorDTO post) { //metodo para agregar sectores
        if (getById(post.getId()) != null){
            return "?err001";
        }
        Map<String, Object> docData = getDocData(post);
        CollectionReference posts = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = posts.document(String.valueOf(post.getId())).set(docData);
        try {
            if (writeResultApiFuture.get() != null){
                return "?createSuccess";
            }
            return "?err";
        } catch (InterruptedException | ExecutionException e) {
            return "?err";
        }
    }

    public Boolean edit(SectorDTO post) { //metodo para editar sectores
        Map<String, Object> docData = getDocData(post);
        docData.values().removeAll(Collections.singleton(null));
        CollectionReference posts = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = posts.document(String.valueOf(post.getId())).update(docData);
        try {
            if (writeResultApiFuture.get() != null){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (InterruptedException | ExecutionException e) {
            return Boolean.FALSE;
        }
    }

    public String delete(String id) { //metodo para eliminar sectores
        if (getById(id) == null){
            return "?err002";
        }
        CollectionReference posts = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = posts.document(id).delete();
        try {
            if (writeResultApiFuture.get() != null){
                return "?deleteSuccess";
            }
            return "?err";
        } catch (InterruptedException | ExecutionException e) {
            return "?err";
        }
    }

    private static Map<String, Object> getDocData(SectorDTO post) {  //metodo para mapear el elemento
        Map<String, Object> docData = new HashMap<>();
        docData.put("sector", post.getId());
        return docData;
    }

    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("SECTOR");
    }//metodo para obtener la coleccion
}
