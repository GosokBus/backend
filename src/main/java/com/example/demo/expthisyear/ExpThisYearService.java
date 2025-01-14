package com.example.demo.expthisyear;

import com.example.demo.expthisyear.ExpThisYear;
import com.example.demo.userinfo.UserInfo;
import com.example.demo.userinfo.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class ExpThisYearService {

    @Autowired
    private ExpThisYearRepository expThisYearRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    public String getTotalExpThisYear(String userId) throws ExecutionException, InterruptedException {
        ExpThisYear expThisYear = expThisYearRepository.findByUserId(userId);
        UserInfo userInfo = userInfoRepository.findById(userId);

        if (expThisYear == null || userInfo == null) {
            return null; // 또는 예외 처리
        }

        try {
            long expThisYearValue = Long.parseLong(expThisYear.getExp());
            long userInfoTotalExp = Long.parseLong(userInfo.get총경험치());
            return String.valueOf(expThisYearValue + userInfoTotalExp);
        } catch (NumberFormatException e) {
            // 2024년 획득한 총 경험치 또는 총경험치 필드가 숫자로 변환할 수 없는 문자열인 경우 예외 처리
            System.err.println("Error parsing expThisYear or totalExp: " + e.getMessage());
            return null; // 또는 예외 처리
        }
    }
}
