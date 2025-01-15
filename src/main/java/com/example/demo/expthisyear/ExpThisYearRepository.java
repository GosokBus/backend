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
        ApiFuture<QuerySnapshot> future = firestore.collection("EXP2024").whereEqualTo("사번", userId).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        if (!documents.isEmpty()) {
            DocumentSnapshot document = documents.get(0);
            ExpThisYear expThisYear = new ExpThisYear();

            // 수동 매핑 시작
            expThisYear.setUserId(document.getString("사번"));
            expThisYear.setExpThisYear(document.getString("2024년 획득한 총 경험치"));
            expThisYear.setLevel(document.getString("레벨"));
            expThisYear.setLeaderQuest(document.getString("리더부여 퀘스트"));
            expThisYear.setFPersonalEval(document.getString("상반기 인사평가"));
            expThisYear.setPart(document.getString("소속"));
            expThisYear.setUserName(document.getString("이름"));
            expThisYear.setCProject(document.getString("전사 프로젝트"));
            expThisYear.setGroup(document.getString("직무그룹"));
            expThisYear.setPartQuest(document.getString("직무별 퀘스트"));
            expThisYear.setSPersonalEval(document.getString("하반기 인사평가"));

            return expThisYear;
        } else {
            return null;
        }
    }
}