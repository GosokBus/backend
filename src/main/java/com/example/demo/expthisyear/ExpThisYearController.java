package com.example.demo.expthisyear;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exp")
public class ExpThisYearController {

    @Autowired
    private ExpThisYearService expThisYearService;

    //올해 획득한 경험치량 반환
    @GetMapping("/{userId}/thisyear")
    public ResponseEntity<String> getExpThisYear(@PathVariable String userId) {
        try {
            String expThisYear = expThisYearService.getExpThisYear(userId);
            if (expThisYear == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(expThisYear);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 전체 경험치 및 레벨 관련 정보 반환
    @GetMapping("/{userId}/expDetails")
    public ResponseEntity<ExpResponse> getExpDetails(@PathVariable String userId) {
        try {
            ExpResponse response = expThisYearService.getExpDetails(userId);

            if (response == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}