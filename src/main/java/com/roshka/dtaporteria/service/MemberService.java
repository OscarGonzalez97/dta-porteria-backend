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

    public String delete(String id) {
        CollectionReference posts = getCollection();
        if (id == null || getById(id) == null) return "?err002";
        ApiFuture<WriteResult> writeResultApiFuture = posts.document(id).delete();
        try {
            if (writeResultApiFuture.get() != null){
                return "?deleteSuccess";
            }
            return "?err001";
        } catch (InterruptedException | ExecutionException ignored) {
            return "?err";
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


    //GRAFICO
    public int[] dataGraficoPie(){
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        int[] members={0,0,0,0,0,0,0,0,0};  //Iniciamos el array con 9 datos que serian los tipos
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                String tipo_miembro = String.valueOf(doc.get("type"));  //aqui obtnemos la fecha exacta en milisegundos
                //getMonthofdatetime nos traera el mes de la fecha que esta en milisegundo
                arrayMiembros(tipo_miembro,members);
            }
            return members;
        } catch (Exception e) {
            return null;
        }
    }
    public int dataTarjetaM(){
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        int canti_defaulter=0;
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                String tipo_miembro = String.valueOf(doc.get("is_defaulter"));
                if (tipo_miembro.equals("Si")){
                    canti_defaulter++;
                }
            }
            return canti_defaulter;
        } catch (Exception e) {
            return 0;
        }
    }
    private void arrayMiembros(String tipo,int[] arrayMembers){
        switch (tipo) {
            case "Socio":
                arrayMembers[0] += 1;
                break;
            case "Staff":
                arrayMembers[1] += 1;
                break;
            case "Guarderia":
                arrayMembers[2] += 1;
                break;
            case "Piscina":
                arrayMembers[3] += 1;
                break;
            case "Tenis":
                arrayMembers[4] += 1;
                break;
            case "Restaurante":
                arrayMembers[5] += 1;
                break;
            case "Gimnasio":
                arrayMembers[6] += 1;
                break;
            case "Eventos":
                arrayMembers[7] += 1;
                break;
            case "Invitado":
                arrayMembers[8] += 1;
                break;
        }
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
    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("MEMBERS");
    }
}
