package com.example.demo.quest.leaderquest;

import com.example.demo.userinfo.Department;
import com.example.demo.userinfo.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        monthlyQuest.setMaxScore(LeaderQuestAchievement.MAX_MONTH.getExp());
        monthlyQuest.setMidScore(LeaderQuestAchievement.MEDIAN_MONTH.getExp());
        conceptualQuests.add(monthlyQuest);

        // "업무개선" 퀘스트
        LeaderQuestDto jobImprovementQuest = new LeaderQuestDto();
        jobImprovementQuest.setQuestName("업무개선");
        jobImprovementQuest.setMaxScore(LeaderQuestAchievement.MAX_JOB.getExp());
        jobImprovementQuest.setMidScore(LeaderQuestAchievement.MEDIAN_JOB.getExp());
        conceptualQuests.add(jobImprovementQuest);

        // 추가적인 퀘스트를 여기에 정의 가능

        // 3. 응답 데이터 구성
        InitialLeaderQuestResponse response = new InitialLeaderQuestResponse();
        response.setUserId(userId);
        response.setDepartment(department.getName());
        response.setLeaderQuests(conceptualQuests);

        return response;
    }

    // 특정 퀘스트의 달력 데이터를 반환하는 메서드
    public List<LeaderQuestCalendarResponse> getQuestCalendarData(String userId, String questName) throws Exception {
        // 사용자 부서 정보 확인
        Department department = userInfoService.getDepartById(userId);
        String collectionName = department.getLeaderQuest();

        // Firestore에서 해당 퀘스트 데이터 조회
        List<LeaderQuest> quests = leaderQuestRepository.findAllByUserId(userId, collectionName);

        // 해당 퀘스트에 대한 달력 데이터 필터링
        List<LeaderQuestCalendarResponse> calendarData = quests.stream()
                .filter(quest -> quest.getQuestName().equalsIgnoreCase(questName)) // 해당 퀘스트 이름 필터링
                .map(quest -> {
                    LeaderQuestCalendarResponse response = new LeaderQuestCalendarResponse();

                    // "월" -> "Month", "주" -> "Week" 변환
                    String translatedTimeType = quest.getMonth() != null ? "Month" : "Week";
                    String timeValue = quest.getMonth() != null ? quest.getMonth() : quest.getWeek();

                    response.setMonthOrWeek(translatedTimeType); // 변환된 값 설정
                    response.setTimeValue(timeValue); // 주차/월 정보
                    response.setAchievement(quest.getAchievement()); // 달성도 정보
                    return response;
                })
                .collect(Collectors.toList());

        return calendarData;
    }

    // 리더부여퀘스트 데이터 응답
    public List<LeaderQuestResponse> getLeaderQuestDetails(String userId) throws Exception {
        // 사용자 부서 정보 확인
        Department department = userInfoService.getDepartById(userId);
        String collectionName = department.getLeaderQuest();

        // Firestore에서 해당 유저의 리더부여퀘스트 데이터 조회
        List<LeaderQuest> quests = leaderQuestRepository.findAllByUserId(userId, collectionName);

        // 응답 데이터를 담을 리스트
        List<LeaderQuestResponse> responses = new ArrayList<>();

        // 퀘스트 데이터를 변환하여 응답 리스트에 추가
        for (LeaderQuest quest : quests) {
            LeaderQuestResponse response = new LeaderQuestResponse();
            response.setQuestName(quest.getQuestName()); // 퀘스트 이름
            response.setRewardExp(quest.getRewardExp()); // 보상 경험치

            // 월 또는 주 정보 설정
            if (quest.getMonth() != null) {
                response.setTimeType("Month");
                response.setTimeValue(quest.getMonth());
            } else if (quest.getWeek() != null) {
                response.setTimeType("Week");
                response.setTimeValue(quest.getWeek());
            }

            responses.add(response);
        }

        return responses;
    }
}
