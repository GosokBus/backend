package com.example.demo.quest.leaderquest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LeaderQuestResponse {
    private String questName; // 퀘스트 이름
    private String rewardExp; // 보상 경험치
    private String timeType;  // 시간 유형 (Month 또는 Week)
    private String timeValue; // 시간 값 (월 또는 주 정보)
}