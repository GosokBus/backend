package com.example.demo.exp.firsteval;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirstPersonnelEvaluationService {

    private final FirstPersonnelEvaluationRepository evaluationRepository;

    @Autowired
    public FirstPersonnelEvaluationService(FirstPersonnelEvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    public FirstPersonnelEvaluationResponse getFirstEvaluation(String userId) throws Exception {
        FirstPersonnelEvaluation evaluation = evaluationRepository.findByUserId(userId);

        if (evaluation == null) {
            throw new Exception("No evaluation data found for userId: " + userId);
        }

        // 응답 객체 생성
        FirstPersonnelEvaluationResponse response = new FirstPersonnelEvaluationResponse();
        response.setGrade(evaluation.getGrade());
        response.setRewardExp(evaluation.getRewardExp());
        return response;
    }
}
