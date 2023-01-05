package com.roshka.dtaporteria.Utils;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.roshka.dtaporteria.Model.MEMBERS;
import com.roshka.dtaporteria.Model.RECORDS;

import java.util.ArrayList;
import java.util.List;

public class ListCollections {
    public static List<MEMBERS> getAllMembers() {
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
    public static List<RECORDS> getAllRecords() {
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
}
