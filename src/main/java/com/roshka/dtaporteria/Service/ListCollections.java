package com.roshka.dtaporteria.Service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.roshka.dtaporteria.Model.MEMBERS;
import com.roshka.dtaporteria.Model.RECORDS;
import com.roshka.dtaporteria.Model.USERS;
import java.util.ArrayList;
import java.util.List;

public abstract class ListCollections {

    protected static List<MEMBERS> getAllMembers() {
        List<MEMBERS> response = new ArrayList<>();
        MEMBERS members;
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference documentReference = dbFirestore.collection("MEMBERS");
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = documentReference.get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                members = doc.toObject(MEMBERS.class);
                assert members != null;
                members.setId_member(Long.valueOf(doc.getId()));
                response.add(members);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }
    protected static List<RECORDS> getAllRecords() {
        List<RECORDS> response = new ArrayList<>();
        RECORDS records;
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference documentReference = dbFirestore.collection("RECORDS");
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = documentReference.get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                records = doc.toObject(RECORDS.class);
                response.add(records);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }
    protected static List<USERS> getAllUsers() {
        List<USERS> response = new ArrayList<>();
        USERS users;
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference documentReference = dbFirestore.collection("USERS");
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = documentReference.get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                users = doc.toObject(USERS.class);
                response.add(users);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }
}
