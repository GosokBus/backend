package com.example.demo.exp;

import com.example.demo.exp.firsteval.FirstPersonnelEvaluationResponse;
import com.example.demo.exp.project.CompanyProjectResponse;
import com.example.demo.exp.secondeval.SecondPersonnelEvaluationResponse;
import com.example.demo.quest.leaderquest.LeaderQuestResponse;
import com.example.demo.quest.leaderquest.LeaderQuestService;
import com.example.demo.quest.partquest.PartQuestResponse;
import com.example.demo.quest.partquest.PartQuestService;
import com.example.demo.exp.firsteval.FirstPersonnelEvaluationService;
import com.example.demo.exp.secondeval.SecondPersonnelEvaluationService;
import com.example.demo.exp.project.CompanyProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exp")
public class ExpController {

    private final PartQuestService partQuestService;
    private final LeaderQuestService leaderQuestService;
    private final FirstPersonnelEvaluationService firstEvalService;
    private final SecondPersonnelEvaluationService secondEvalService;
    private final CompanyProjectService companyProjectService;

    @Autowired
    public ExpController(
            PartQuestService partQuestService,
            LeaderQuestService leaderQuestService,
            FirstPersonnelEvaluationService firstEvalService,
            SecondPersonnelEvaluationService secondEvalService,
            CompanyProjectService companyProjectService
    ) {
        this.partQuestService = partQuestService;
        this.leaderQuestService = leaderQuestService;
        this.firstEvalService = firstEvalService;
        this.secondEvalService = secondEvalService;
        this.companyProjectService = companyProjectService;
    }

    // 모든 경험치 데이터를 카테고리별로 응답
    @GetMapping("/{userId}/all")
    public ResponseEntity<Map<String, Object>> getAllExperienceData(@PathVariable String userId) {
        try {
            // 직무별 퀘스트 데이터
            List<PartQuestResponse> partQuests = partQuestService.getFilteredPartQuests(userId);

            // 리더부여 퀘스트 데이터
            List<LeaderQuestResponse> leaderQuests = leaderQuestService.getLeaderQuestDetails(userId);

            // 상반기 인사평가 데이터
            FirstPersonnelEvaluationResponse firstEvaluation = firstEvalService.getFirstEvaluation(userId);

            // 하반기 인사평가 데이터
            SecondPersonnelEvaluationResponse secondEvaluation = secondEvalService.getSecondEvaluation(userId);

            // 전사 프로젝트 데이터
            List<CompanyProjectResponse> companyProjects;
            Object companyProjectsData = companyProjectService.getCompanyProjects(userId);

            // 데이터 타입에 따른 처리
            if (companyProjectsData instanceof List<?>) {
                companyProjects = (List<CompanyProjectResponse>) companyProjectsData;
            } else {
                companyProjects = new ArrayList<>();
                companyProjects.add((CompanyProjectResponse) companyProjectsData);
            }

            // 카테고리별 데이터 맵핑
            Map<String, Object> response = new HashMap<>();
            response.put("partQuests", partQuests); // 직무별 퀘스트
            response.put("leaderQuests", leaderQuests); // 리더부여 퀘스트
            response.put("firstEvaluation", firstEvaluation); // 상반기 인사평가
            response.put("secondEvaluation", secondEvaluation); // 하반기 인사평가
            response.put("companyProjects", companyProjects); // 전사 프로젝트

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error fetching experience data for userId: " + userId);
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}
