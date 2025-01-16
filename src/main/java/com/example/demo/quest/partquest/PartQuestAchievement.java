package com.example.demo.quest.partquest;

import lombok.Getter;

@Getter
public enum PartQuestAchievement {
    MAX("Max", 80),
    MEDIUM("Medium", 40);

    private final String standard;
    private final int rewardExp;

    PartQuestAchievement(String standard, int rewardExp) {
        this.standard = standard;
        this.rewardExp = rewardExp;
    }


}
