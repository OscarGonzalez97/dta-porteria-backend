package com.roshka.dtaporteria.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.roshka.dtaporteria.Model.MEMBERS;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class MEMBERSService {
    public String createMEMBERS(MEMBERS members) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("MEMBERS").document(String.valueOf(members.getId_member())).set(members);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String updateMEMBERS(MEMBERS members) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        List<String> listaAtributos = Update.getMembers(members);
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("MEMBERS").document(String.valueOf(members.getId_member())).set(members);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String deleteMEMBERS(String documentID) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection("MEMBERS").document(documentID).delete();
        return "Succesfully deleted " + documentID;
    }

    public MEMBERS getMEMBERS(String documentID) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("MEMBERS").document(documentID);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        MEMBERS members;
        if (document.exists()) {
            members = document.toObject(MEMBERS.class);
            return members;
        }
        return null;
    }
    public List<MEMBERS> getAllMEMBERS() throws ExecutionException, InterruptedException {
        return ListCollections.getAllMembers();
    }
}
