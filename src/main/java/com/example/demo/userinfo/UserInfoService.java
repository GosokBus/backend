package com.example.demo.userinfo;

import com.example.demo.login.LoginRequestDTO;
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

        System.out.println("Fetched UserInfo: " + userInfo);

        Department department = Department.fromName(userInfo.getPart());
        if (department == null) {
            System.out.println("Department not found for user part: " + userInfo.getPart());
            throw new Exception("Department not found for the user");
        }

        System.out.println("Fetched Department: " + department);
        return department;
    }


    public UserInfo login(LoginRequestDTO loginRequest) throws ExecutionException, InterruptedException {
        UserInfo userInfo = userInfoRepository.login(loginRequest.getLoginId());

        System.out.println(userInfo.getUserId());
        System.out.println(userInfo.getUserName());
        System.out.println(userInfo.getLoginId());
        System.out.println(userInfo.getLevel());
        System.out.println(userInfo.getExpInHave());
        System.out.println(userInfo.getGroup());
        System.out.println(userInfo.getPart());
        System.out.println(userInfo.getJoinDay());
        System.out.println(userInfo.getPassword());

        if (userInfo == null) {
            return null; // 또는 예외 처리
        }

        if(!userInfo.getPassword().equals(loginRequest.getPassword())) {
            return null;
        }

        return userInfo;
    }

    //비밀번호 변경
    public void updatePassword(String userId, String newPassword) throws Exception {
        // 유저가 존재하는지 확인
        UserInfo userInfo = userInfoRepository.findById(userId);
        if (userInfo == null) {
            throw new Exception("User not found");
        }

        // 비밀번호 업데이트
        userInfoRepository.updatePassword(userId, newPassword);
        System.out.println("Password updated for userId: " + userId);
    }
}
