package com.example.demo.firestore;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
    @PostConstruct
    public void firestore() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("/Users/imjaehyeon/Desktop/3-2/spring/blaybus/GosokbusCRUD3/src/main/resources/gosock-bus-firebase-adminsdk-mi0c1-269c27d623.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

    }
}
