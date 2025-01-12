package com.example.demo.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FirestoreReadService {

    private final Firestore firestore;

    public FirestoreReadService(Firestore firestore) {
        this.firestore = firestore;
    }

    public List<Map<String, Object>> getCollectionData(String collectionName) throws ExecutionException, InterruptedException {
        CollectionReference collection = firestore.collection(collectionName);
        ApiFuture<QuerySnapshot> querySnapshot = collection.get();

        List<Map<String, Object>> documents = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            documents.add(document.getData());
        }
        return documents;
    }
}
