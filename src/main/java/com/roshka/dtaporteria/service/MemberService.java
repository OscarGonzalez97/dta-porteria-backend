package com.roshka.dtaporteria.service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.roshka.dtaporteria.config.FirebaseInitializer;
import com.roshka.dtaporteria.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class MemberService {

    @Autowired
    private FirebaseInitializer firebase;

    public MemberDTO getById(String id) {
        DocumentReference docRef = getCollection().document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()){
                MemberDTO member = document.toObject(MemberDTO.class);
                return member;
            }
            return null;
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public Boolean getById(Integer idmember) {
        DocumentReference docRef = getCollection().document(idmember.toString());
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            return document.exists();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public List<MemberDTO> list() {
        List<MemberDTO> response = new ArrayList<>();
        MemberDTO post;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                post = doc.toObject(MemberDTO.class);
                response.add(post);
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean add(MemberDTO post) {
        String documentId = post.getId();
        Map<String, Object> docData = getDocData(post);
        CollectionReference posts = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = posts.document(documentId).set(docData);
        try {
            if (writeResultApiFuture.get() != null){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (InterruptedException | ExecutionException e) {
            return Boolean.FALSE;
        }
    }

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
    public Boolean update(MemberDTO member) {
        Map<String, Object> docData = getDocData(member);
        docData.values().remove(Collections.singleton(null));
        CollectionReference posts = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = posts.document(String.valueOf(member.getId())).update(docData);
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

    private static Map<String, Object> getDocData(MemberDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("created_by", post.getCreated_by());
        docData.put("id_member", post.getId_member());
        docData.put("ci", post.getCi());
        docData.put("is_defaulter", post.getIs_defaulter());
        docData.put("name", post.getName());
        docData.put("photo", post.getPhoto());
        docData.put("surname", post.getSurname());
        docData.put("type", post.getType());
        docData.put("fecha_vencimiento", post.getFecha_vencimiento());
        return docData;
    }
    public boolean someAttributeIsNull(MemberDTO member) {
        if ((member.getId_member() == null)
                || (member.getCreated_by() == "")
                || (member.getFecha_vencimiento() == "" && !Objects.equals(member.getType(), "Socio"))
                || (member.getName() == "")
                || (member.getSurname() == "")
                || (member.getPhoto() == "")
                || (member.getType() == "")
                || (member.getIs_defaulter() == "")) {
            return true;
        }
        return false;
    }
    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("MEMBERS");
    }
}
