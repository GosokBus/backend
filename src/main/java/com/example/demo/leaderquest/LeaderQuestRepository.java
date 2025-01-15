package com.example.demo.leaderquest;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class LeaderQuestRepository {

    private final Firestore firestore;
    @Autowired
    public LeaderQuestRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    // LeaderQuest 목록 조회 (userId 기준) - 메서드 이름 변경 및 userId 파라미터 추가
    public List<LeaderQuest> findAllByUserId(String userId, String collectionName) throws ExecutionException, InterruptedException {
        CollectionReference collectionRef = firestore.collection(collectionName);
        Query query = collectionRef.whereEqualTo("사번", userId);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<LeaderQuest> leaderQuests = new ArrayList<>();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            LeaderQuest leaderQuest = new LeaderQuest();
            leaderQuest.setUserId(document.getString("사번") != null ? document.getString("사번") : "Unknown");
            leaderQuest.setUserName(document.getString("대상자") != null ? document.getString("대상자") : "Unknown");
            leaderQuest.setQuestName(document.getString("리더 부여 퀘스트명") != null ? document.getString("리더 부여 퀘스트명") : "Unknown");

            // Achievement 열거형 매핑
            String achievementString = document.getString("달성내용");
            Achievement achievement = Achievement.fromString(achievementString);
            leaderQuest.setAchievement(achievement != null ? achievement.getLabel() : "Unknown");

            leaderQuest.setRewardExp(document.getString("부여 경험치") != null ? document.getString("부여 경험치") : "0");
            leaderQuest.setNote(document.getString("비고") != null ? document.getString("비고") : "");
            leaderQuest.setMonth(document.getString("월") != null ? document.getString("월") : "0");
            leaderQuest.setWeek(document.getString("주") != null ? document.getString("주") : "0");

            // 디버깅 로그
            System.out.println("Document ID: " + document.getId());
            System.out.println("Document Data: " + document.getData());
            System.out.println("LeaderQuest: " + leaderQuest);

            leaderQuests.add(leaderQuest);
        }
        return leaderQuests;
    }
}
