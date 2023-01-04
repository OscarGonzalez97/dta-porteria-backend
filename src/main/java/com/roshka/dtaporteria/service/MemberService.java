package com.roshka.dtaporteria.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.roshka.dtaporteria.config.FirebaseInitializer;
import com.roshka.dtaporteria.dto.MemberDTO;
import com.roshka.dtaporteria.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class MemberService implements MemberRepository {

    @Autowired
    private FirebaseInitializer firebase;

    @Override
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

    @Override
    public List<MemberDTO> list() {
        List<MemberDTO> response = new ArrayList<>();
        MemberDTO post;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                post = doc.toObject(MemberDTO.class);
                assert post != null;
                post.setId(doc.getId());
                response.add(post);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean add(MemberDTO post) {
        Map<String, Object> docData = getDocData(post);
        CollectionReference posts = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = posts.document(String.valueOf(post.getId_member())).set(docData);
        try {
            if (writeResultApiFuture.get() != null){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (InterruptedException | ExecutionException e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean edit(String id, MemberDTO post) {
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

    @Override
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

    private static Map<String, Object> getDocData(MemberDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("created_by", post.getCreated_by());
        docData.put("id_member", post.getId_member());
        docData.put("is_defaulter", post.getIs_defaulter());
        docData.put("name", post.getName());
        docData.put("photo", post.getPhoto());
        docData.put("surname", post.getSurname());
        docData.put("type", post.getType());
        return docData;
    }

    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("MEMBERS");
    }
}
