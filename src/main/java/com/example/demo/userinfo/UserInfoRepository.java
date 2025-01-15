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
            UserInfo userInfo = new UserInfo();
            userInfo.set사번(document.getId());
            userInfo.set이름(document.getString("이름"));
            userInfo.set입사일(document.getString("입사일"));
            userInfo.set소속(document.getString("소속"));
            userInfo.set직무그룹(document.getString("직무그룹"));
            userInfo.set레벨(Level.fromFirestoreValue(document.getString("레벨"))); // Firestore 값을 Level Enum으로 변환
            userInfo.set아이디(document.getString("아이디"));
            userInfo.set기본패스워드(document.getString("기본패스워드"));
            userInfo.set총경험치(document.getString("총경험치"));

            return userInfo;
        } else {
            return null;
        }
    }
}