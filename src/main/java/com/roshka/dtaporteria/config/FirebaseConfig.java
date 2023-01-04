package com.roshka.dtaporteria.config;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
@Configuration
@Service
public class FirebaseConfig {
        @PostConstruct
        private void firestore() throws Exception{
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/ServiceAccountKey.json");
            FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
            FirebaseApp.initializeApp(options);
            if (FirebaseApp.getApps().isEmpty()){
                FirebaseApp.initializeApp(options);
            }
        }
        public Firestore getFirestore(){
            return FirestoreClient.getFirestore();
        }
}
