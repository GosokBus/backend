package com.example.demo.exp.secondeval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecondPersonnelEvaluationService {

    private final SecondPersonnelEvaluationRepository evaluationRepository;

    @Autowired
    public SecondPersonnelEvaluationService(SecondPersonnelEvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    public SecondPersonnelEvaluationResponse getSecondEvaluation(String userId) throws Exception {
        SecondPersonnelEvaluation evaluation = evaluationRepository.findByUserId(userId);

        if (evaluation == null) {
            throw new Exception("No evaluation data found for userId: " + userId);
        }

        // 응답 객체 생성
        SecondPersonnelEvaluationResponse response = new SecondPersonnelEvaluationResponse();
        response.setGrade(evaluation.getGrade());
        response.setRewardExp(evaluation.getRewardExp());
        return response;
    }
}

