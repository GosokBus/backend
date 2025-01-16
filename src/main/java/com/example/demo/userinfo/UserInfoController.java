package com.example.demo.userinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.concurrent.ExecutionException;

@RestController
public class UserInfoController {

    private final UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    //유저 정보 전체 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserInfo> getMyInfo(@PathVariable String userId) throws ExecutionException, InterruptedException {
        UserInfo userInfo = userInfoService.getMyInfo(userId);
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // userInfo.get레벨()은 Level Enum 타입
        if (userInfo.getLevel() != null) {
            System.out.println("Level (Firestore Value): " + userInfo.getLevel().getFirestoreValue());
            System.out.println("Level (Enum Name): " + userInfo.getLevel().name());
        }

        return ResponseEntity.ok(userInfo);
    }

    //유저의 총 경험치 필드 조회
    @GetMapping("/user/{userId}/lastExp")
    public ResponseEntity<String> getTotalExp(@PathVariable String userId) {
        try {
            String totalExp = userInfoService.getTotalExp(userId);
            return ResponseEntity.ok(totalExp);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //비밀번호 변경
    @PatchMapping("/user/{userId}/updatePassword")
    public ResponseEntity<String> updatePassword(@PathVariable String userId, @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        try {
            userInfoService.updatePassword(userId, passwordUpdateRequest.getNewPassword());
            return ResponseEntity.ok("Password updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
