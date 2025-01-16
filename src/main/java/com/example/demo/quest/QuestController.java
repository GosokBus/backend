package com.example.demo.quest;

import com.example.demo.quest.leaderquest.InitialLeaderQuestResponse;
import com.example.demo.quest.leaderquest.LeaderQuest;
import com.example.demo.quest.leaderquest.LeaderQuestCalendarResponse;
import com.example.demo.quest.leaderquest.LeaderQuestService;
import com.example.demo.quest.partquest.InitialPartQuestResponse;
import com.example.demo.quest.partquest.PartQuestCalendarResponse;
import com.example.demo.quest.partquest.PartQuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quest")
public class QuestController {

    private final LeaderQuestService leaderQuestService;
    private final PartQuestService partQuestService;

    @Autowired
    public QuestController(LeaderQuestService leaderQuestService, PartQuestService partQuestService) {
        this.leaderQuestService = leaderQuestService;
        this.partQuestService = partQuestService;
    }

    // 초기화면 - LeaderQuest와 PartQuest 데이터를 포함
    @GetMapping("/{userId}")
    public ResponseEntity<InitialQuestResponse> getInitialQuestData(@PathVariable String userId) {
        try {
            InitialLeaderQuestResponse leaderResponse = leaderQuestService.getInitialQuestData(userId);
            InitialPartQuestResponse partResponse = partQuestService.getInitialPartQuestData(userId); // PartQuest 데이터 추가

            // LeaderQuest와 PartQuest 데이터를 포함하는 응답 객체 생성
            InitialQuestResponse response = new InitialQuestResponse();
            response.setUserId(userId);
            response.setDepartment(leaderResponse.getDepartment());
            response.setLeaderQuests(leaderResponse.getLeaderQuests());
            response.setPartQuests(partResponse.getPartQuests()); // PartQuest 데이터 포함

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 특정 사용자의 LeaderQuest 목록 조회
    @GetMapping("/{userId}/leader")
    public ResponseEntity<List<LeaderQuest>> getLeaderQuestsByUserId(@PathVariable String userId) throws Exception {
        List<LeaderQuest> leaderQuests = leaderQuestService.getLeaderQuestsByUserId(userId);
        return new ResponseEntity<>(leaderQuests, HttpStatus.OK);
    }

    // 리더부여 퀘스트 중 "월특근" 퀘스트 정보 목록 조회
    @GetMapping("/{userId}/leader/{questName}")
    public ResponseEntity<List<LeaderQuestCalendarResponse>> getMonthlyQuestInfo(@PathVariable String userId , @PathVariable String questName) throws Exception {
        List<LeaderQuestCalendarResponse> response = leaderQuestService.getQuestCalendarData(userId, questName);
        return ResponseEntity.ok(response);
    }


    // 직무별 퀘스트 조회
    @GetMapping("/{userId}/part")
    public ResponseEntity<List<PartQuestCalendarResponse>> getPartQuests(@PathVariable String userId) {
        try {
            List<PartQuestCalendarResponse> partQuests = partQuestService.getAllPartQuests(userId);
            return ResponseEntity.ok(partQuests);
        } catch (Exception e) {
            System.out.println("Error occurred while fetching PartQuests: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
