package com.example.demo.userinfo;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfo {

    // @DocumentId
    //@PropertyName("사번")
    private String userId;
    //@PropertyName("이름")
    private String userName;
    //@PropertyName("입사일")
    private String joinDay;
    //@PropertyName("소속")
    private String part;
    //@PropertyName("직무그룹")
    private String group;
    //@PropertyName("레벨")
    private Level level;
    //@PropertyName("아이디")
    private String loginId;
    //@PropertyName("기본패스워드")
    private String password;
    //@PropertyName("총경험치")
    private String expInHave;
}
