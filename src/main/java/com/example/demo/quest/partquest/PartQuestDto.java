package com.example.demo.quest.partquest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartQuestDto {
    private String questName; // 퀘스트 이름
    private int maxScore;     // MAX 기준 점수
    private int midScore;     // MID 기준 점수
}
