package com.roshka.dtaporteria.Service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.roshka.dtaporteria.Model.Members;
import com.roshka.dtaporteria.Model.Records;
import com.roshka.dtaporteria.Model.Users;
import java.util.ArrayList;
import java.util.List;

public abstract class ListCollections {

    protected static List<Members> getAllMembers() {
        List<Members> response = new ArrayList<>();
        Members members;
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference documentReference = dbFirestore.collection("MEMBERS");
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = documentReference.get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                members = doc.toObject(Members.class);
                assert members != null;
                members.setId_member(Long.valueOf(doc.getId()));
                response.add(members);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }
    protected static List<Records> getAllRecords() {
        List<Records> response = new ArrayList<>();
        Records records;
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference documentReference = dbFirestore.collection("RECORDS");
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = documentReference.get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                records = doc.toObject(Records.class);
                response.add(records);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }
    protected static List<Users> getAllUsers() {
        List<Users> response = new ArrayList<>();
        Users users;
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference documentReference = dbFirestore.collection("USERS");
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = documentReference.get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                users = doc.toObject(Users.class);
                response.add(users);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }
}
