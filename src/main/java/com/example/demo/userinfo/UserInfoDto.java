package com.example.demo.userinfo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInfoDto {
    private String 사번;
    private String 이름;
    private String 입사일;
    private String 소속; // String 타입으로 변경
    private String 직무그룹;
    private String 레벨;
    private String 아이디;
    private String 기본패스워드;
    private String 총경험치;

    public UserInfoDto(UserInfo userInfo) {
        this.사번 = userInfo.get사번();
        this.이름 = userInfo.get이름();
        this.입사일 = userInfo.get입사일();
        this.소속 = userInfo.get소속(); // String 타입으로 변경
        this.직무그룹 = userInfo.get직무그룹();
        this.아이디 = userInfo.get아이디();
        this.기본패스워드 = userInfo.get기본패스워드();
        this.총경험치 = userInfo.get총경험치();

        if (userInfo.get레벨() != null) {
            Level levelEnum = Level.fromFirestoreValue(String.valueOf(userInfo.get레벨()));

            if (levelEnum != null) {
                this.레벨 = levelEnum.getFirestoreValue();
            } else {
                // Level Enum에 정의되지 않은 값이 들어온 경우 처리
                System.err.println("Invalid Level value: " + userInfo.get레벨());
                this.레벨 = null; // 또는 기본값 설정
            }
        } else {
            // userInfo.get레벨()이 null인 경우 처리
            this.레벨 = null; // 또는 기본값 설정
        }
    }
}