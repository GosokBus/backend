package com.example.demo.jwt;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

@Repository
public class RefreshTokenRepository {

    private final Firestore firestore;


    public RefreshTokenRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public RefreshToken findByUserId(String userId) throws ExecutionException, InterruptedException {

        DocumentReference docRef = firestore.collection("refreshToken").document(userId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            return document.toObject(RefreshToken.class); // UserInfo.class로 변경
        } else {
            return null;
        }

    }

    public RefreshToken findByRefreshToken(String refreshToken) throws ExecutionException, InterruptedException {
        Query query = firestore.collection("refreshToken").whereEqualTo("refreshToken", refreshToken);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        if (!querySnapshot.get().isEmpty()) {
            DocumentSnapshot document = querySnapshot.get().getDocuments().get(0);
            return document.toObject(RefreshToken.class);
        } else {
            return null;
        }
    }

    public void save(RefreshToken refreshToken) throws ExecutionException, InterruptedException {
        firestore.collection("refreshToken")
                .document(refreshToken.getUserId())
                .set(refreshToken)
                .get();
    }
}
