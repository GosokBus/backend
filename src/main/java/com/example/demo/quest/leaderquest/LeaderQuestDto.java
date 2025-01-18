package com.example.demo.quest.leaderquest;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class LeaderQuestDto {
    private String questName; // 퀘스트 이름
    private int maxScore;     // MAX 기준 점수
    private int midScore;     // MID 기준 점수

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaderQuestDto that = (LeaderQuestDto) o;
        return Objects.equals(questName, that.questName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questName);
    }
}