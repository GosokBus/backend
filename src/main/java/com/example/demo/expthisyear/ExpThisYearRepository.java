package com.example.demo.expthisyear;

import com.example.demo.expthisyear.ExpThisYear;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;
import com.google.api.core.ApiFuture;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class ExpThisYearRepository {

    private final Firestore firestore;

    @Autowired
    public ExpThisYearRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public ExpThisYear findByUserId(String userId) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = firestore.collection("EXP2024").whereEqualTo("userId", userId).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        if (!documents.isEmpty()) {
            // 사번은 고유해야 하므로, 첫 번째 문서만 반환
            return documents.get(0).toObject(ExpThisYear.class);
        } else {
            return null;
        }
    }
}