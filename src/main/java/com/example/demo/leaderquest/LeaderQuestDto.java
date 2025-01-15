package com.example.demo.leaderquest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaderQuestDto {
    private String questName; // 퀘스트 이름
    private int maxScore;     // MAX 기준 점수
    private int midScore;     // MID 기준 점수
    private int rewardExp;    // 지급 경험치
    private String achievementLabel; // 달성도 이름 ("Max", "Median")
}