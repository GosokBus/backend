package com.example.demo.userinfo;

import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutionException;

@Repository
public class UserInfoRepository {

    private final Firestore firestore;

    @Autowired
    public UserInfoRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public UserInfo findById(String userId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("userInfo").document(userId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            return document.toObject(UserInfo.class); // UserInfo.class로 변경
        } else {
            return null;
        }
    }

}