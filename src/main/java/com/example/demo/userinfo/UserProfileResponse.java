package com.example.demo.userinfo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponse {
    private String userName; // 이름
    private String level;    // 레벨
    private String userId;   // 사번
    private String part;     // 소속
    private String joinDay;  // 입사일
    private String avatarId; // 아바타 id
}
