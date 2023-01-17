package com.roshka.dtaporteria.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.roshka.dtaporteria.config.FirebaseInitializer;
import com.roshka.dtaporteria.dto.MemberDTO;
import com.roshka.dtaporteria.dto.RecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class RecordService {
    @Autowired
    private FirebaseInitializer firebase;

    public RecordDTO getById(String id) { //metodo para obtener un record por su id
        DocumentReference docRef = getCollection().document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()){
                RecordDTO record = document.toObject(RecordDTO.class);
                return record;
            }
            return null;
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public List<RecordDTO> list(){ //metodo para listar todos los records
        List<RecordDTO> response = new ArrayList<>();
        RecordDTO post;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                post = doc.toObject(RecordDTO.class);
                response.add(post);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }


    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("RECORDS");
    } //coleccion de records
}
