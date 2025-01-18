package com.example.demo.quest.partquest;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PartQuestRepository {

    private final Firestore firestore;

    @Autowired
    public PartQuestRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    // Firestore에서 모든 직무별 퀘스트 조회
    public List<PartQuestCalendarResponse> findAll(String collectionName) throws Exception {
        List<PartQuestCalendarResponse> responses = new ArrayList<>();
        System.out.println("Querying Firestore collection: " + collectionName);

        ApiFuture<QuerySnapshot> future = firestore.collection(collectionName).get(); // 조건 없이 모든 문서 가져오기
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        if (documents.isEmpty()) {
            System.out.println("No documents found in collection: " + collectionName);
            return responses;
        }

        for (QueryDocumentSnapshot document : documents) {
            // 수동 매핑
            PartQuestCalendarResponse response = new PartQuestCalendarResponse();
            response.setWeek(document.getString("주차")); // 주차 정보
            String rewardExpStr = document.getString("부여 경험치");

            // 달성 정도 계산
            if (rewardExpStr != null) {
                int rewardExp = Integer.parseInt(rewardExpStr);
                response.setAchievement(getAchievementByExp(rewardExp));
            } else {
                response.setAchievement("Unknown");
            }

            System.out.println("Fetched document: " + document.getData());
            responses.add(response);
        }

        return responses;
    }

    private String getAchievementByExp(int rewardExp) {
        for (PartQuestAchievement achievement : PartQuestAchievement.values()) {
            if (achievement.getRewardExp() == rewardExp) {
                return achievement.getStandard();
            }
        }
        return "Unknown"; // 해당하지 않는 경우
    }

    // Firestore에서 모든 직무별 퀘스트 조회 (PartQuest 타입)
    public List<PartQuest> findAllAsPartQuest(String collectionName) throws Exception {
        List<PartQuest> partQuests = new ArrayList<>();
        System.out.println("Querying Firestore collection for PartQuest: " + collectionName);

        ApiFuture<QuerySnapshot> future = firestore.collection(collectionName).get(); // 조건 없이 모든 문서 가져오기
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        if (documents.isEmpty()) {
            System.out.println("No documents found in collection: " + collectionName);
            return partQuests;
        }

        for (QueryDocumentSnapshot document : documents) {
            // 수동 매핑
            PartQuest partQuest = new PartQuest();
            partQuest.setRewardExp(document.getString("부여 경험치")); // 보상 경험치
            partQuest.setWeek(document.getString("주차")); // 주차 정보
            partQuest.setNote(document.getString("비고")); // 비고 정보

            System.out.println("Fetched document: " + document.getData());
            partQuests.add(partQuest);
        }

        return partQuests;
    }
}
