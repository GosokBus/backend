package com.example.demo.exp.secondeval;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SecondPersonnelEvaluationRepository {

    private final Firestore firestore;

    @Autowired
    public SecondPersonnelEvaluationRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public SecondPersonnelEvaluation findByUserId(String userId) throws Exception {
        ApiFuture<QuerySnapshot> future = firestore.collection("secondPersonnelEvaluation")
                .whereEqualTo("사번", userId)
                .get();

        if (!future.get().isEmpty()) {
            DocumentSnapshot document = future.get().getDocuments().get(0);

            SecondPersonnelEvaluation evaluation = new SecondPersonnelEvaluation();
            evaluation.setGrade(document.getString("인사평가 등급"));
            evaluation.setRewardExp(document.getString("부여 경험치"));
            return evaluation;
        }
        return null; // 사번에 해당하는 문서가 없는 경우
    }
}

