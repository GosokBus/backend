package com.example.demo.userinfo;

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

    //유저 정보 불러오기
    public UserInfo getMyInfo(String userId) throws ExecutionException, InterruptedException {
        return userInfoRepository.findById(userId);
    }

    //유저의 총 경험치 필드값 불러오기
    public String getTotalExp(String userId) throws Exception {
        UserInfo userInfo = userInfoRepository.findById(userId);
        if (userInfo == null) {
            throw new Exception("User not found");
        }
        return userInfo.getExpInHave();
    }

    //유저 부서 정보 불러오기
    public Department getDepartById(String userId) throws Exception {
        UserInfo userInfo = userInfoRepository.findById(userId);
        if (userInfo == null) {
            throw new Exception("User not found");
        }

        Department department = Department.fromName(userInfo.getPart());
        if (department == null) {
            throw new Exception("Department not found for the user");
        }

        return department;
    }
}
