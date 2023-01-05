package com.roshka.dtaporteria.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.roshka.dtaporteria.config.FirebaseInitializer;
import com.roshka.dtaporteria.dto.MemberDTO;
import com.roshka.dtaporteria.dto.RecordDTO;
import com.roshka.dtaporteria.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecordService implements RecordRepository {
    @Autowired
    private FirebaseInitializer firebase;

    @Override
    public List<RecordDTO> list(){
        List<RecordDTO> response = new ArrayList<>();
        RecordDTO post;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                post = doc.toObject(RecordDTO.class);
                Objects.requireNonNull(post).setId(doc.getId());
                response.add(post);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }

//    private static Map<String, Object> getDocData(RecordDTO post) {
//        Map<String, Object> docData = new HashMap<>();
//        docData.put("ci_member", post.getCi_member());
//        docData.put("ci_portero", post.getCi_portero());
//        docData.put("date_time", post.getDate_time());
//        docData.put("email_portero", post.getEmail_portero());
//        docData.put("id_member", post.getId_member());
//        docData.put("is_defaulter", post.getIs_defaulter());
//        docData.put("is_exit", post.getIs_exit());
//        docData.put("is_walk", post.getIs_walk());
//        docData.put("name_member", post.getName_member());
//        docData.put("name_portero", post.getName_portero());
//        docData.put("photo", post.getPhoto());
//        docData.put("surname_member", post.getSurname_member());
//        docData.put("surname_portero", post.getSurname_portero());
//        docData.put("type", post.getType());
//        return docData;
//    }

    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("RECORDS");
    }
}
