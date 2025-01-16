package com.example.demo.expthisyear.exprecent;

import com.google.cloud.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecentExpService {

    @Autowired
    private RecentExpRepository recentExpRepository;
    public List<RecentExpResponse> getRecentExp(String userId) throws Exception {

        List<Map<String, Object>> recentExpList = recentExpRepository.findRecentExpByUserId(userId);

        if (recentExpList == null) {
            return null;
        }

        return recentExpList.stream().map(exp -> {
            RecentExpResponse response = new RecentExpResponse();
            response.setExp((String) exp.get("exp"));
            response.setQuest((String) exp.get("quest"));

            Timestamp timestamp = (Timestamp) exp.get("createdAt");
            if (timestamp != null) {
                response.setCreatedAt(timestamp.toDate().toString()); // ISO-8601 형식
            }

            return response;
        }).collect(Collectors.toList());
    }
}
