package com.example.demo.quest.partquest;

import com.example.demo.quest.leaderquest.LeaderQuestCalendarResponse;
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

    // 직무별 퀘스트 정보 반환
    public List<PartQuestCalendarResponse> getAllPartQuests(String userId) throws Exception {
        // 사용자 부서 정보에서 컬렉션 이름 가져오기
        Department department = userInfoService.getDepartById(userId);
        String collectionName = department.getPartQuest();

        // Firestore에서 컬렉션의 모든 문서 가져오기
        List<PartQuestCalendarResponse> partQuests = partQuestRepository.findAll(collectionName);
        if (partQuests.isEmpty()) {
            System.out.println("No PartQuest data found for collection: " + collectionName);
        }

        return partQuests;
    }

    // 부여 경험치가 0 이상인 직무별 퀘스트 데이터만 반환
    public List<PartQuestResponse> getFilteredPartQuests(String userId) throws Exception {
        // 사용자 부서 정보에서 컬렉션 이름 가져오기
        Department department = userInfoService.getDepartById(userId);
        String collectionName = department.getPartQuest();

        // Firestore에서 컬렉션의 모든 문서 가져오기
        List<PartQuest> allPartQuests = partQuestRepository.findAllAsPartQuest(collectionName);

        // 응답 데이터를 담을 리스트
        List<PartQuestResponse> responses = new ArrayList<>();

        // 필터링 및 변환
        for (PartQuest quest : allPartQuests) {
            try {
                int rewardExp = Integer.parseInt(String.valueOf(quest.getRewardExp()));
                if (rewardExp > 0) {
                    PartQuestResponse response = new PartQuestResponse();
                    response.setQuestName("직무별 퀘스트"); // 퀘스트 이름
                    response.setRewardExp(rewardExp); // 보상 경험치
                    response.setWeek(quest.getWeek()); // 주차 정보

                    responses.add(response);
                }
            } catch (NumberFormatException | NullPointerException e) {
                System.err.println("Error parsing rewardExp for quest: " + quest.getRewardExp());
            }
        }

        return responses;
    }

}
