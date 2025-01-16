package com.example.demo.quest.partquest;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartQuestResponse {
    private String questName;   // 퀘스트 이름
    private int rewardExp;      // 보상 경험치
    private String week;        // 주차 정보
}
