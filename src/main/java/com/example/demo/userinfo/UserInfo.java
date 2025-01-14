package com.example.demo.userinfo;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfo {

    @DocumentId
    private String 사번;
    private String 이름;
    private String 입사일;
    private String 소속;
    private String  직무그룹;
    private String 레벨;
    private String 아이디;
    private String 기본패스워드;
    private String 총경험치;
}
