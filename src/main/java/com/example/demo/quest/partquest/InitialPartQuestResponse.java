package com.example.demo.quest.partquest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InitialPartQuestResponse {
    private String userId; // 사용자 ID
    private String department; // 부서 이름
    private List<PartQuestDto> partQuests; // 직무별 퀘스트 목록
}
