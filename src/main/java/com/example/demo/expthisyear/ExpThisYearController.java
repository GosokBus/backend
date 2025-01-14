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

    @GetMapping("/{userId}/total")
    public ResponseEntity<String> getTotalExpThisYear(@PathVariable String userId) {
        try {
            String totalExpThisYear = expThisYearService.getTotalExpThisYear(userId);
            if (totalExpThisYear == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(totalExpThisYear);
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}