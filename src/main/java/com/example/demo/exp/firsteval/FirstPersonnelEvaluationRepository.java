package com.example.demo.exp.firsteval;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FirstPersonnelEvaluationRepository {

    private final Firestore firestore;

    @Autowired
    public FirstPersonnelEvaluationRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public FirstPersonnelEvaluation findByUserId(String userId) throws Exception {
        ApiFuture<QuerySnapshot> future = firestore.collection("firstPersonnelEvaluation")
                .whereEqualTo("사번", userId)
                .get();

        if (!future.get().isEmpty()) {
            DocumentSnapshot document = future.get().getDocuments().get(0);

            FirstPersonnelEvaluation evaluation = new FirstPersonnelEvaluation();
            evaluation.setGrade(document.getString("인사평가 등급"));
            evaluation.setRewardExp(document.getString("부여 경험치"));
            return evaluation;
        }
        return null; // 사번에 해당하는 문서가 없는 경우
    }
}

