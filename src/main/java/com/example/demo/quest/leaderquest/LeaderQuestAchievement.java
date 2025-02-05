package com.example.demo.quest.leaderquest;

import lombok.Getter;

@Getter
public enum LeaderQuestAchievement {
    MAX_MONTH("Max", 100),
    MEDIAN_MONTH("Median", 50),
    MAX_JOB("Max", 67),
    MEDIAN_JOB("Median", 33);

    private final String label; // 달성도 이름
    private final int exp;      // 경험치 값

    LeaderQuestAchievement(String label, int exp) {
        this.label = label;
        this.exp = exp;
    }

    // 달성도 문자열로 Achievement 찾기
    public static LeaderQuestAchievement fromString(String achievement) {
        for (LeaderQuestAchievement value : LeaderQuestAchievement.values()) {
            if (value.label.equalsIgnoreCase(achievement)) {
                return value;
            }
        }
        return null; // 일치하는 값이 없으면 null 반환
    }
}
