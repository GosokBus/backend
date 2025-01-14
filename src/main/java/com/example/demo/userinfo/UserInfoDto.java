package com.example.demo.userinfo;

import com.example.demo.userinfo.Department;
import com.example.demo.userinfo.Level;
import com.example.demo.userinfo.UserInfo;
import com.google.cloud.Date;
import lombok.Getter;
import lombok.Setter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class UserInfoDto {
    private String 사번;
    private String 이름;
    private String 입사일; // String 타입 (API 응답용)
    private Department 소속;
    private String  직무그룹;
    private Level 레벨;
    private String 아이디;
    private String 기본패스워드;
    private String 총경험치;

    public UserInfoDto(UserInfo userInfo) {
        this.사번 = userInfo.get사번();
        this.이름 = userInfo.get이름();
        this.입사일 = userInfo.get입사일(); // UserInfo의 String 타입 입사일을 그대로 할당
        this.소속 = Department.of(userInfo.get소속()); // of 메서드 사용
        this.직무그룹 = userInfo.get직무그룹();
        this.레벨 = Level.valueOf(userInfo.get레벨().replace("-", "_"));
        this.아이디 = userInfo.get아이디();
        this.기본패스워드 = userInfo.get기본패스워드();
        this.총경험치 = userInfo.get총경험치();
    }
}