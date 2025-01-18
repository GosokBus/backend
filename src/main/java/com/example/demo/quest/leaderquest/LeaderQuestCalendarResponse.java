package com.example.demo.quest.leaderquest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaderQuestCalendarResponse {
    private String monthOrWeek; // "월" 또는 "주" 판단 정보
    private String timeValue;   // 주차/월 정보
    private String achievement; // 달성도 정보
}