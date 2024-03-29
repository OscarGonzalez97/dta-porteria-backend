package com.roshka.dtaporteria.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.roshka.dtaporteria.config.FirebaseInitializer;
import com.roshka.dtaporteria.dto.TypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class TypeService {
    @Autowired
    private FirebaseInitializer firebase;

    public Map<String, Object> getById(String id) {
        DocumentReference docRef = getCollection().document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            return document.getData();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }
    public List<TypeDTO> list(){
        List<TypeDTO> response = new ArrayList<>();
        TypeDTO post;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                post = doc.toObject(TypeDTO.class);
                response.add(post);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }
    public Boolean add(TypeDTO post) {
        Map<String, Object> docData = getDocData(post);
        CollectionReference posts = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = posts.document(String.valueOf(post.getType())).set(docData);
        try {
            if (writeResultApiFuture.get() != null){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (InterruptedException | ExecutionException e) {
            return Boolean.FALSE;
        }
    }
    public Boolean edit(String id, TypeDTO post) {
        Map<String, Object> docData = getDocData(post);
        docData.values().removeAll(Collections.singleton(null));
        CollectionReference posts = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = posts.document(id).update(docData);
        try {
            if (writeResultApiFuture.get() != null){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (InterruptedException | ExecutionException e) {
            return Boolean.FALSE;
        }
    }
    public Boolean delete(String id) {
        CollectionReference posts = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = posts.document(id).delete();
        try {
            if (writeResultApiFuture.get() != null){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (InterruptedException | ExecutionException e) {
            return Boolean.FALSE;
        }
    }
    private static Map<String, Object> getDocData(TypeDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("type", post.getType());
        return docData;
    }
    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("TYPE");
    }
}
