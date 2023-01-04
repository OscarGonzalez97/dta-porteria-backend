package com.roshka.dtaporteria.Service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.roshka.dtaporteria.CRUD.RECORDS;
import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutionException;

@Service
public class RECORDSService {
    public String createRECORDS(RECORDS records) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("RECORDS").document().set(records);
        return  collectionsApiFuture.get().getUpdateTime().toString();
    }
    public String updateRECORDS(RECORDS records) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("RECORDS").document().set(records);
        return  collectionApiFuture.get().getUpdateTime().toString();
    }
    public String deleteRECORDS(String documentID){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection("RECORDS").document().delete();
        return  "Succesfully deleted";
    }
    public RECORDS getRECORDS(String documentID) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("RECORDS").document(documentID);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        RECORDS records;
        if (document.exists()){
            records = document.toObject(RECORDS.class);
            return records;
        }
        return  null;
    }
}
