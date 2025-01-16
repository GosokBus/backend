package com.example.demo.quest;

import com.example.demo.quest.leaderquest.LeaderQuestDto;
import com.example.demo.quest.partquest.PartQuestDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InitialQuestResponse {
    private String userId;                // 사용자 ID
    private String department;            // 부서 이름
    private List<LeaderQuestDto> leaderQuests; // 리더 부여 퀘스트 목록
    private List<PartQuestDto> partQuests;     // 직무별 퀘스트 목록
}