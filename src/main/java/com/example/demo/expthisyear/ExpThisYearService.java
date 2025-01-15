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

    //올해 획득한 경험치 반환
    public String getExpThisYear(String userId) throws ExecutionException, InterruptedException {
        ExpThisYear expThisYear = expThisYearRepository.findByUserId(userId);

        if (expThisYear == null) {
            return null;
        }

        try {
            if (expThisYear.getExpThisYear() != null) {
                return expThisYear.getExpThisYear();
            } else {
                System.err.println("expThisYear.getExpThisYear() is null for userId: " + userId); // 로그 추가
                return null;
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing expThisYear or totalExp for userId: " + userId + ", error: " + e.getMessage());
            System.err.println("expThisYear.getExpThisYear(): " + expThisYear.getExpThisYear());
            return null;
        }
    }

    //경험치 전체 총합 반환
    public String getTotalExpThisYear(String userId) throws ExecutionException, InterruptedException {
        ExpThisYear expThisYear = expThisYearRepository.findByUserId(userId);
        UserInfo userInfo = userInfoRepository.findById(userId);

        if (expThisYear == null || userInfo == null) {
            return null; // 또는 예외 처리
        }

        try {
            // expThisYear가 null이 아니므로, getExpThisYear()가 null이면 Firestore 필드가 비어있는 것
            long expThisYearValue = 0;
            if (expThisYear.getExpThisYear() != null) {
                expThisYearValue = Long.parseLong(expThisYear.getExpThisYear().replace(",",""));
            } else {
                System.err.println("expThisYear.getExpThisYear() is null for userId: " + userId); // 로그 추가
            }
            // 쉼표 제거 후 long타입으로 변환
            long userInfoTotalExp = Long.parseLong(userInfo.getExpInHave().replace(",", ""));

            return String.valueOf(expThisYearValue + userInfoTotalExp);
        } catch (NumberFormatException e) {
            // 2024년 획득한 총 경험치 또는 총경험치 필드가 숫자로 변환할 수 없는 문자열인 경우 예외 처리
            System.err.println("Error parsing expThisYear or totalExp for userId: " + userId + ", error: " + e.getMessage());
            System.err.println("expThisYear.getExpThisYear(): " + expThisYear.getExpThisYear());
            System.err.println("userInfo.getExpInHave(): " + userInfo.getExpInHave());
            return null; // 또는 예외 처리
        }
    }


}
