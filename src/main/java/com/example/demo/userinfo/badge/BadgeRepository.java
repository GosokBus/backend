package com.example.demo.userinfo.badge;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class BadgeRepository {

    private final Firestore firestore;

    @Autowired
    public BadgeRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    // 특정 유저의 뱃지 리스트 조회
    public List<Badge> findByUserId(String userId) throws ExecutionException, InterruptedException {
        CollectionReference badgeCollection = firestore.collection("badge");
        ApiFuture<QuerySnapshot> future = badgeCollection.whereEqualTo("userId", userId).get();
        QuerySnapshot querySnapshot = future.get();

        List<Badge> badges = new ArrayList<>();
        if (!querySnapshot.isEmpty()) {
            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                Badge badge = new Badge();
                badge.setBadgeId(document.getString("badgeId"));
                badge.setStatus(document.getBoolean("status"));
                badges.add(badge);
            }
        }
        return badges;
    }

    // 특정 뱃지의 상태 업데이트
    public void updateBadgeStatus(String badgeId, boolean status) throws ExecutionException, InterruptedException {
        CollectionReference badgeCollection = firestore.collection("badge");
        ApiFuture<QuerySnapshot> future = badgeCollection.whereEqualTo("badgeId", badgeId).get();
        QuerySnapshot querySnapshot = future.get();

        if (!querySnapshot.isEmpty()) {
            DocumentReference docRef = querySnapshot.getDocuments().get(0).getReference();
            docRef.update("status", status).get();
        }
    }
}