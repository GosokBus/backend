package com.example.demo.quest.partquest;

import com.example.demo.userinfo.Department;
import com.example.demo.userinfo.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartQuestService {

    private final PartQuestRepository partQuestRepository;
    private final UserInfoService userInfoService;

    @Autowired
    public PartQuestService(PartQuestRepository partQuestRepository, UserInfoService userInfoService) {
        this.partQuestRepository = partQuestRepository;
        this.userInfoService = userInfoService;
    }

    //초기화면 불러오기
    public InitialPartQuestResponse getInitialPartQuestData(String userId) throws Exception {
        // 1. 사용자 부서 정보 확인
        Department department = userInfoService.getDepartById(userId);

        // 2. 직무별 퀘스트 데이터 구성 (하드코딩된 개념 데이터)
        List<PartQuestDto> partQuests = new ArrayList<>();

        // 직무별 퀘스트 생성
        PartQuestDto partQuest = new PartQuestDto();
        partQuest.setQuestName("직무별퀘스트");
        partQuest.setMaxScore(80); // MAX 기준 점수
        partQuest.setMidScore(40); // MID 기준 점수
        partQuests.add(partQuest);

        // 3. 응답 데이터 구성
        InitialPartQuestResponse response = new InitialPartQuestResponse();
        response.setUserId(userId);
        response.setDepartment(department.getName());
        response.setPartQuests(partQuests);

        return response;
    }
}
