package com.example.demo.expthisyear.exprecent;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
public class RecentExpRepository {

    private final Firestore firestore;

    @Autowired
    public RecentExpRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public List<Map<String, Object>> findRecentExpByUserId(String userId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("recentExp").document(userId);
        ApiFuture<DocumentSnapshot> future = docRef.get();

        DocumentSnapshot document = future.get();

        if (!document.exists()) {
            return null;
        }

        Object recentExpObject = document.get("recentExp");

        if (recentExpObject instanceof List<?>) {
            return (List<Map<String, Object>>) recentExpObject;
        } else {
            return null;
        }
    }
}

