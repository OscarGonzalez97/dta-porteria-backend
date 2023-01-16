package com.roshka.dtaporteria.service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.roshka.dtaporteria.config.FirebaseInitializer;
import com.roshka.dtaporteria.dto.MemberDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class MemberService {

    private final FirebaseInitializer firebase;

    public MemberService(FirebaseInitializer firebase) {
        this.firebase = firebase;
    }

    public MemberDTO getById(String id) {
        DocumentReference docRef = getCollection().document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                return document.toObject(MemberDTO.class);
            }
            return null;
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }
    public Boolean getByIdmember(String id) {
        ApiFuture<QuerySnapshot> future = getCollection().whereEqualTo("id_member", id).get();
        try {
            QuerySnapshot document = future.get();
            if (document.getDocuments().isEmpty()) {
                return false;
            }
            return true;
        } catch (InterruptedException | ExecutionException e) {
            return false;
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
        Map<String, Object> docData = getDocData(post);
        CollectionReference posts = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = posts.document().set(docData);
        try {
            if (writeResultApiFuture.get() != null) {
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
            if (writeResultApiFuture.get() != null) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (InterruptedException | ExecutionException e) {
            return Boolean.FALSE;
        }
    }

    public void delete(String id) {
        CollectionReference posts = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = posts.document(id).delete();
        try {
            writeResultApiFuture.get();
        } catch (InterruptedException | ExecutionException ignored) {
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
    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("MEMBERS");
    }

    public void AddMembersByList(List<MemberDTO> miembros) {
        Firestore db = getCollection().getFirestore();
        WriteBatch batch = db.batch();
        for(MemberDTO miembro : miembros){
            DocumentReference memberReference = db.collection("MEMBERS").document();
            batch.set(memberReference, miembro);
        }
        batch.commit();
    }
}
