package com.example.demo.leaderquest;

import com.example.demo.userinfo.Department;
import com.example.demo.userinfo.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public InitialQuestResponse getInitialQuestData(String userId) throws Exception {
        // 1. 사용자 부서 정보 확인
        Department department = userInfoService.getDepartById(userId);
        String collectionName = department.getLeaderQuest();

        // 2. 리더부여 퀘스트 목록 가져오기
        List<LeaderQuest> leaderQuests = leaderQuestRepository.findAllByUserId(userId, collectionName);

        // 3. 완료된 퀘스트만 필터링 및 DTO 변환
        List<LeaderQuestDto> quests = leaderQuests.stream()
                .filter(quest -> quest.getAchievement() != null && !quest.getAchievement().isEmpty()) // 완료된 퀘스트만 필터링
                .map(quest -> {
                    Achievement achievement = Achievement.fromString(quest.getAchievement());

                    LeaderQuestDto dto = new LeaderQuestDto();
                    dto.setQuestName(quest.getQuestName());
                    dto.setMaxScore(80); // 예제 값
                    dto.setMidScore(40); // 예제 값
                    if (achievement != null) {
                        dto.setRewardExp(achievement.getExp());
                        dto.setAchievementLabel(achievement.getLabel());
                    } else {
                        dto.setRewardExp(0);
                        dto.setAchievementLabel("Unknown");
                    }
                    return dto;
                })
                .collect(Collectors.toList());

        // 4. 응답 데이터 구성
        InitialQuestResponse response = new InitialQuestResponse();
        response.setUserId(userId);
        response.setDepartment(department.getName());
        response.setLeaderQuests(quests);

        return response;
    }
}
