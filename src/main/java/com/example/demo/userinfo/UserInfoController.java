package com.example.demo.userinfo;

import com.example.demo.userinfo.avatar.AvatarService;
import com.example.demo.userinfo.avatar.AvatarUpdateRequest;
import com.example.demo.userinfo.badge.Badge;
import com.example.demo.userinfo.badge.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class UserInfoController {

    private final UserInfoService userInfoService;
    private final AvatarService avatarService;
    private final BadgeService badgeService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService, AvatarService avatarService, BadgeService badgeService) {
        this.userInfoService = userInfoService;
        this.avatarService = avatarService;
        this.badgeService = badgeService;
    }

    //유저 정보 전체 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserInfoWithBadges> getMyInfo(@PathVariable String userId) throws ExecutionException, InterruptedException {
        UserInfo userInfo = userInfoService.getMyInfo(userId);
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        try {
            // 뱃지 가져오기 (status가 true인 것을 우선, 부족하면 false 추가)
            List<Badge> topThreeBadges = badgeService.getTopThreeBadges(userId);

            // 응답 객체 생성
            UserInfoWithBadges response = new UserInfoWithBadges();
            response.setUserName(userInfo.getUserName());
            response.setLevel(userInfo.getLevel().getFirestoreValue());
            response.setUserId(userInfo.getUserId());
            response.setPart(userInfo.getPart());
            response.setJoinDay(userInfo.getJoinDay());
            response.setBadges(topThreeBadges); // 상위 3개의 뱃지 추가

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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

    //프로필 초기화면
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable String userId) {
        try {
            // 유저 정보 불러오기
            UserInfo userInfo = userInfoService.getMyInfo(userId);

            if (userInfo == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // 아바타 정보 불러오기
            String avatarId = avatarService.getAvatarId(userId);

            // 뱃지 정보 불러오기
            List<Badge> badges = badgeService.getUserBadges(userId);

            // 응답 객체 생성
            UserProfileResponse profileResponse = new UserProfileResponse();
            profileResponse.setUserName(userInfo.getUserName());
            profileResponse.setLevel(userInfo.getLevel().getFirestoreValue());
            profileResponse.setUserId(userInfo.getUserId());
            profileResponse.setPart(userInfo.getPart());
            profileResponse.setJoinDay(userInfo.getJoinDay());
            profileResponse.setAvatarId(avatarId); // 아바타 ID 추가
            profileResponse.setBadges(badges);     // 뱃지 정보 추가


            return ResponseEntity.ok(profileResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //비밀번호 변경
    @PatchMapping("/profile/{userId}/updatePassword")
    public ResponseEntity<String> updatePassword(@PathVariable String userId, @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        try {
            userInfoService.updatePassword(userId, passwordUpdateRequest.getNewPassword());
            return ResponseEntity.ok("Password updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //아바타 변경
    @PatchMapping("/profile/{userId}/updateAvatar")
    public ResponseEntity<String> updateAvatar(@PathVariable String userId, @RequestBody AvatarUpdateRequest avatarUpdateRequest) {
        try {
            // 아바타 업데이트
            avatarService.updateUserAvatar(userId, avatarUpdateRequest.getAvatarId());
            return ResponseEntity.ok("Avatar updated successfully");
        } catch (Exception e) {
            if (e.getMessage().contains("Avatar document not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
