package com.roshka.dtaporteria.Service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.roshka.dtaporteria.Model.Records;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class RecordsService {
    public String createRECORDS(Records records) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("RECORDS").document().set(records);
        return  collectionsApiFuture.get().getUpdateTime().toString();
    }
    public Records getRECORDS(String documentID) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("RECORDS").document(documentID);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Records records;
        if (document.exists()){
            records = document.toObject(Records.class);
            return records;
        }
        return  null;
    }
    public List<Records> getAllRECORDS() throws ExecutionException, InterruptedException {
        return ListCollections.getAllRecords();
    }
}
