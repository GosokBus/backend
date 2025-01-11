package com.example.demo.firestore;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirestoreConfig {

    @Bean
    public Firestore firestore() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/gosock-bus-firebase-adminsdk-mi0c1-269c27d623.json");

        FirestoreOptions options = FirestoreOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseId("gosokbus")
                .build();

        return options.getService();
    }

}
