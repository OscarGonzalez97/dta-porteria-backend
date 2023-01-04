package com.roshka.dtaporteria.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.WriteResult;
import com.roshka.dtaporteria.config.FirebaseInitializer;
import com.roshka.dtaporteria.dto.MemberDTO;
import com.roshka.dtaporteria.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private FirebaseInitializer firebase;

    @Override
    public List<MemberDTO> list() {
        List<MemberDTO> response = new ArrayList<>();

        return response;
    }

    @Override
    public Boolean add(MemberDTO post) {
        Map<String, Object> docData = new HashMap<String, Object>();
        docData.put("created_by", post.getCreated_by());
        docData.put("id_member", post.getId_member());
        docData.put("is_defaulter", post.getIs_defaulter());
        docData.put("name", post.getName());
        docData.put("photo", post.getPhoto());
        docData.put("surname", post.getSurname());
        docData.put("type", post.getType());
        CollectionReference posts = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = posts.document().set(docData);
        try {
            if (writeResultApiFuture.get() != null){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("MEMBERS");
    }

    @Override
    public Boolean edit(String id, MemberDTO post) {
        return null;
    }

    @Override
    public Boolean delete(String id) {
        return null;
    }
}
