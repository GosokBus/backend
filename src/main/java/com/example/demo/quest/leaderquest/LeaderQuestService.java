package com.example.demo.quest.leaderquest;

import com.example.demo.userinfo.Department;
import com.example.demo.userinfo.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeaderQuestService {

    private final LeaderQuestRepository leaderQuestRepository;
    private final UserInfoService userInfoService;

    @Autowired
    public LeaderQuestService(LeaderQuestRepository leaderQuestRepository, UserInfoService userInfoService) {
        this.leaderQuestRepository = leaderQuestRepository;
        this.userInfoService = userInfoService;
    }

    // 특정 사용자의 LeaderQuest 목록 조회
    public List<LeaderQuest> getLeaderQuestsByUserId(String userId) throws Exception {
        // UserInfoService를 통해 Department 객체 가져오기
        Department department = userInfoService.getDepartById(userId);
        String collectionName = department.getLeaderQuest();
        return leaderQuestRepository.findAllByUserId(userId, collectionName);
    }

    //초기화면 불러오기
    public InitialLeaderQuestResponse getInitialQuestData(String userId) throws Exception {
        // 1. 사용자 부서 정보 확인
        Department department = userInfoService.getDepartById(userId);

        // 2. 퀘스트 이름별로 개념 데이터를 생성
        List<LeaderQuestDto> conceptualQuests = new ArrayList<>();

        // "월특근" 퀘스트
        LeaderQuestDto monthlyQuest = new LeaderQuestDto();
        monthlyQuest.setQuestName("월특근");
        monthlyQuest.setMaxScore(Achievement.MAX_MONTH.getExp());
        monthlyQuest.setMidScore(Achievement.MEDIAN_MONTH.getExp());
        conceptualQuests.add(monthlyQuest);

        // "업무개선" 퀘스트
        LeaderQuestDto jobImprovementQuest = new LeaderQuestDto();
        jobImprovementQuest.setQuestName("업무개선");
        jobImprovementQuest.setMaxScore(Achievement.MAX_JOB.getExp());
        jobImprovementQuest.setMidScore(Achievement.MEDIAN_JOB.getExp());
        conceptualQuests.add(jobImprovementQuest);

        // 추가적인 퀘스트를 여기에 정의 가능

        // 3. 응답 데이터 구성
        InitialLeaderQuestResponse response = new InitialLeaderQuestResponse();
        response.setUserId(userId);
        response.setDepartment(department.getName());
        response.setLeaderQuests(conceptualQuests);

        return response;
    }
}
