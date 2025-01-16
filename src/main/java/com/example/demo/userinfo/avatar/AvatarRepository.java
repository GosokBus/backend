package com.example.demo.userinfo.avatar;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

@Repository
public class AvatarRepository {

    private final Firestore firestore;

    @Autowired
    public AvatarRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    // Firestore에서 아바타 정보 조회 (수동 매핑)
    public Avatar findByUserId(String userId) throws ExecutionException, InterruptedException {
        // userId 필드 기반 쿼리
        CollectionReference avatarCollection = firestore.collection("avatar");
        ApiFuture<QuerySnapshot> future = avatarCollection.whereEqualTo("userId", userId).get();
        QuerySnapshot querySnapshot = future.get();

        if (!querySnapshot.isEmpty()) {
            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
            Avatar avatar = new Avatar();
            avatar.setUserId(document.getString("userId"));
            avatar.setAvatarId(document.getString("avatarId"));
            return avatar;
        } else {
            return null;
        }
    }

    public void updateAvatar(String userId, String avatarId) throws Exception {
        CollectionReference avatarCollection = firestore.collection("avatar");

        // userId 기반으로 문서 쿼리
        ApiFuture<QuerySnapshot> future = avatarCollection.whereEqualTo("userId", userId).get();
        QuerySnapshot querySnapshot = future.get();

        // 문서가 존재하는지 확인
        if (querySnapshot.isEmpty()) {
            throw new Exception("Avatar document not found for userId: " + userId);
        }

        // 첫 번째 문서를 가져와 업데이트 수행
        DocumentReference docRef = querySnapshot.getDocuments().get(0).getReference();
        docRef.update("avatarId", avatarId).get(); // avatarId 필드 업데이트
        System.out.println("Avatar updated successfully for userId: " + userId);
    }
}
