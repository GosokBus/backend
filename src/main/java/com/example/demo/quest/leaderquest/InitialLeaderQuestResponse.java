package com.example.demo.quest.leaderquest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InitialLeaderQuestResponse {
    private String userId; // 사용자 ID
    private String department; // 사용자 부서 이름
    private List<LeaderQuestDto> leaderQuests; // 리더부여 퀘스트 목록
}
