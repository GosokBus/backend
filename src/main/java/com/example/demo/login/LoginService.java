package com.example.demo.login;

import com.example.demo.userinfo.UserInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserInfoRepository userInfoRepository;


    public LoginService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }


}
