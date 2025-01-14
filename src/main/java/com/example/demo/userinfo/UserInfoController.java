package com.example.demo.userinfo;

import com.example.demo.userinfo.UserInfoDto;
import com.example.demo.userinfo.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import java.util.concurrent.ExecutionException;

@RestController
public class UserInfoController {

    private final UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserInfoDto> getMyInfo(@PathVariable String userId) throws ExecutionException, InterruptedException {
        UserInfoDto userInfoDto = userInfoService.getMyInfo(userId);
        if (userInfoDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(userInfoDto);
    }

    @GetMapping("/user/{userId}/lastExp")
    public ResponseEntity<String> getTotalExp(@PathVariable String userId) {
        try {
            String totalExp = userInfoService.getTotalExp(userId);
            return ResponseEntity.ok(totalExp);
        } catch (Exception e) {
            // 예외 처리 (사용자를 찾을 수 없는 경우, Firestore 통신 오류 등)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
