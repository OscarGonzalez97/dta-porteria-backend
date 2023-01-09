package com.roshka.dtaporteria.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.roshka.dtaporteria.config.FirebaseInitializer;
import com.roshka.dtaporteria.dto.MemberDTO;
import com.roshka.dtaporteria.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class UserService{
    @Autowired
    private FirebaseInitializer firebase;

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

    public Boolean crear(UserDTO post){
        Map<String, Object> docData = getDocData(post);
        CollectionReference posts = getCollection();
        System.out.println(post.getId());
        ApiFuture<WriteResult> writeResultApiFuture = posts.document(String.valueOf(post.getId())).set(docData);
        try {
            if (writeResultApiFuture.get() != null){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (InterruptedException | ExecutionException e) {
            return Boolean.FALSE;
        }
    }

    private static Map<String, Object> getDocData(UserDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("active", post.getActive());
        docData.put("ci", post.getCi());
        docData.put("name", post.getName());
        docData.put("surname", post.getSurname());
        docData.put("rol", post.getRol());
        System.out.println(docData);
        return docData;
    }
    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("USERS");
    }
}
