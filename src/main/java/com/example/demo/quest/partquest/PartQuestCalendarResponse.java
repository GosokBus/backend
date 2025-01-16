package com.example.demo.quest.partquest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PartQuestCalendarResponse {
    private String week; // 주차 정보
    private String achievement; // 달성 정도
}