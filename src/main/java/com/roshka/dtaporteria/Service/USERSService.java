package com.roshka.dtaporteria.Service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.roshka.dtaporteria.Model.USERS;
import com.roshka.dtaporteria.Utils.Update;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class USERSService {
    public String createUSERS(USERS users, String email) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("USERS").document(email).set(users);
        return  collectionsApiFuture.get().getUpdateTime().toString();
    }
    public String updateUSERS(USERS users, String email) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        List<String> listaAtributos= Update.getUsers(users);
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("USERS").document(email).set(users, SetOptions.mergeFields(listaAtributos));
        return  collectionApiFuture.get().getUpdateTime().toString();
    }

    public String deleteUSERS(String documentID){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection("USERS").document().delete();
        return  "Succesfully deleted";
    }
    public USERS getUSERS(String documentID) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("USERS").document(documentID);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        USERS users;
        if (document.exists()){
            users = document.toObject(USERS.class);
            return users;
        }
        return  null;
    }
}
