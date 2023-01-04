package com.roshka.dtaporteria.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firestore.v1.Write;
import org.springframework.stereotype.Service;
import com.roshka.dtaporteria.CRUD.MEMBERS;
import com.roshka.dtaporteria.*;
import java.util.concurrent.ExecutionException;

@Service
public class MEMBERSService {
    public String createMEMBERS(MEMBERS members) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("MEMBERS").document(members.getCedula()).set(members);
        return  collectionsApiFuture.get().getUpdateTime().toString();
    }
    public String updateMEMBERS(MEMBERS members) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("MEMBERS").document(members.getCedula()).set(members);
        return  collectionApiFuture.get().getUpdateTime().toString();
    }
    public String deleteMEMBERS(String documentID){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection("MEMBERS").document(documentID).delete();
        return  "Succesfully deleted "+ documentID;
    }
    public MEMBERS getMEMBERS(String documentID) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("MEMBERS").document(documentID);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        MEMBERS members;
        if (document.exists()){
            members = document.toObject(MEMBERS.class);
            return members;
        }
        return  null;
    }
}
