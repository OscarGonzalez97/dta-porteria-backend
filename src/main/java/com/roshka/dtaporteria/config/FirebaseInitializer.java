package com.roshka.dtaporteria.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseInitializer {

    @Primary
    @PostConstruct
    private Firestore iniFirestore() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("./firebaseAccountInfo.json");
        FirebaseOptions options = new FirebaseOptions.Builder().
                setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://porteria-dta-test.firebaseio.com/")
                .build();
        FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
        return FirestoreClient.getFirestore(firebaseApp);
    }

    @Bean
    public Firestore getFirestore() {return FirestoreClient.getFirestore();}

    @Bean
    public FirebaseAuth getFirebaseAuth(){return FirebaseAuth.getInstance();}



}
