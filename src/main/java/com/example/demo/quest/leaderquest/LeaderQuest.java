package com.example.demo.quest.leaderquest;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LeaderQuest {

    @DocumentId
    @PropertyName("사번")
    private String userId;

    @PropertyName("대상자")
    private String userName;
    @PropertyName("리더 부여 퀘스트명")
    private String questName;
    @PropertyName("달성내용")
    private String achievement;
    @PropertyName("부여 경험치")
    private String rewardExp;
    @PropertyName("비고")
    private String note;
    @PropertyName("월")
    private String month;
    @PropertyName("주")
    private String week;
}
