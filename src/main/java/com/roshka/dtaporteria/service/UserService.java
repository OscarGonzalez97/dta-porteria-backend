package com.roshka.dtaporteria.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.roshka.dtaporteria.config.FirebaseInitializer;
import com.roshka.dtaporteria.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class UserService{
    @Autowired
    private FirebaseInitializer firebase;
    public UserDTO getById(String id) {
        if (id == null) return null;
        DocumentReference docRef = getCollection().document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()){
                UserDTO user = document.toObject(UserDTO.class);
                return user;
            }
            return null;
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public List<UserDTO> list(){
        List<UserDTO> response = new ArrayList<>();
        UserDTO post;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                post = doc.toObject(UserDTO.class);
                response.add(post);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    public String crear(UserDTO post){
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

    public String update(UserDTO post) {
        if (getById(post.getId()) == null){
            return "?err002";
        }
        Map<String, Object> docData = getDocData(post);
        docData.values().removeAll(Collections.singleton(null));
        CollectionReference posts = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = posts.document(String.valueOf(post.getId())).update(docData);
        try {
            if (writeResultApiFuture.get() != null){
                return "?editSuccess";
            }
            return "?err";
        } catch (InterruptedException | ExecutionException e) {
            return "?err";
        }
    }

    public String disable(String id){
        UserDTO user = getById(id);
        user.setActive("disabled");
        return update(user);
    }

    public String delete(String id){
        CollectionReference posts = getCollection();
        if (id == null || getById(id) == null) return "?err002";
        ApiFuture<WriteResult> writeResultApiFuture = posts.document(id).delete();
        try {
            if (writeResultApiFuture.get() != null){
                return "?deleteSuccess";
            }
            return "?err001";
        } catch (InterruptedException | ExecutionException e) {
            return "?err001";
        }
    }

    private static Map<String, Object> getDocData(UserDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("active", post.getActive());
        docData.put("ci", post.getCi());
        docData.put("name", post.getName());
        docData.put("surname", post.getSurname());
        docData.put("rol", post.getRol());
        return docData;
    }
    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("USERS");
    }
}
