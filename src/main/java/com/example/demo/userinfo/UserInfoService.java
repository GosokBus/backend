package com.example.demo.userinfo;

import com.example.demo.userinfo.UserInfo;
import com.example.demo.userinfo.UserInfoDto;
import com.example.demo.userinfo.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfoDto getMyInfo(String userId) throws ExecutionException, InterruptedException {
        UserInfo userInfo = userInfoRepository.findById(userId);
        if (userInfo == null) {
            return null; // 또는 예외 처리
        }
        return new UserInfoDto(userInfo);
    }

    public String getTotalExp(String userId) throws Exception {
        UserInfo userInfo = userInfoRepository.findById(userId);
        if (userInfo == null) {
            throw new Exception("User not found"); // 또는 적절한 예외 처리
        }
        return userInfo.get총경험치(); // String 타입으로 반환
    }
}
