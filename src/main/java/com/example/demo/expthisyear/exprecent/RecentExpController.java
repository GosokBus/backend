package com.example.demo.expthisyear.exprecent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recentExp")
public class RecentExpController {

    @Autowired
    private RecentExpService recentExpService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<RecentExpResponse>> getRecentExp(@PathVariable String userId) {
        try {
            List<RecentExpResponse> recentExpList = recentExpService.getRecentExp(userId);

            if (recentExpList == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            return ResponseEntity.ok(recentExpList);
        } catch (Exception e) {
            // 예외 발생 시 로그 추가
            System.err.println("[Controller] Error while fetching recentExp data for userId: " + userId);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}


