package com.example.demo.login;

import com.google.cloud.firestore.annotation.PropertyName;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class LoginRequestDTO {

    // @PropertyName("아이디")
    private String loginId;
    // @PropertyName("기본패스워드")
    private String password;

}
