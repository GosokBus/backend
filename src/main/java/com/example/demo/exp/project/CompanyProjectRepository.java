package com.example.demo.exp.project;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyProjectRepository {

    private final Firestore firestore;

    @Autowired
    public CompanyProjectRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public CompanyProject findByUserId(String userId) throws Exception {
        ApiFuture<QuerySnapshot> future = firestore.collection("companyProject")
                .whereEqualTo("사번", userId)
                .get();

        if (!future.get().isEmpty()) {
            DocumentSnapshot document = future.get().getDocuments().get(0);

            CompanyProject project = new CompanyProject();
            project.setProjectName(document.getString("전사 프로젝트명"));
            project.setRewardExp(document.getString("부여 경험치"));
            return project;
        }
        return null; // 사번에 해당하는 문서가 없는 경우
    }
}
