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

    //유저 전체 정보 조회
    public UserInfo findById(String userId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("userInfo").document(userId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(document.getId());
            userInfo.setUserName(document.getString("이름"));
            userInfo.setJoinDay(document.getString("입사일"));
            userInfo.setPart(document.getString("소속"));
            userInfo.setGroup(document.getString("직무그룹"));
            userInfo.setLevel(Level.fromFirestoreValue(document.getString("레벨"))); // Firestore 값을 Level Enum으로 변환
            userInfo.setLoginId(document.getString("아이디"));
            userInfo.setPassword(document.getString("기본패스워드"));
            userInfo.setExpInHave(document.getString("총경험치"));

            return userInfo;
        } else {
            return null;
        }
    }
}