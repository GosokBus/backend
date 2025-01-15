package com.example.demo.leaderquest;

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
public class LeaderQuestController {

    private final LeaderQuestService leaderQuestService;

    @Autowired
    public LeaderQuestController(LeaderQuestService leaderQuestService) {
        this.leaderQuestService = leaderQuestService;
    }

    // 특정 사용자의 LeaderQuest 목록 조회
    @GetMapping("/{userId}/leader")
    public ResponseEntity<List<LeaderQuest>> getLeaderQuestsByUserId(@PathVariable String userId) throws Exception {
        List<LeaderQuest> leaderQuests = leaderQuestService.getLeaderQuestsByUserId(userId);
        return new ResponseEntity<>(leaderQuests, HttpStatus.OK);
    }

    //초기 화면
    @GetMapping("/{userId}")
    public ResponseEntity<InitialQuestResponse> getInitialQuestData(@PathVariable String userId) {
        try {
            InitialQuestResponse response = leaderQuestService.getInitialQuestData(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
