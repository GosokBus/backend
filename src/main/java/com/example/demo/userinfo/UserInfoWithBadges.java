package com.example.demo.userinfo;

import com.example.demo.userinfo.badge.Badge;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfoWithBadges {
    private String userName; // 이름
    private String level;    // 레벨
    private String userId;   // 사번
    private String part;     // 소속
    private String joinDay;  // 입사일
    private List<Badge> badges; // 상위 3개의 뱃지
}