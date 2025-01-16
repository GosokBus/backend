package com.example.demo.expthisyear;

import com.example.demo.expthisyear.ExpThisYear;
import com.example.demo.userinfo.Level;
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

    // 전체 경험치와 관련 데이터를 계산하여 반환
    public ExpResponse getExpDetails(String userId) throws ExecutionException, InterruptedException {
        ExpThisYear expThisYear = expThisYearRepository.findByUserId(userId);
        UserInfo userInfo = userInfoRepository.findById(userId);

        if (expThisYear == null || userInfo == null) {
            return null;
        }

        // 올해 획득한 경험치
        long totalExpThisYear = parseLongOrDefault(expThisYear.getExpThisYear(), 0);
        // userInfo에 저장된 총 경험치
        long userTotalExpInInfo = parseLongOrDefault(userInfo.getExpInHave(), 0);
        // 총 경험치 = userInfo의 총 경험치 + 올해 획득 경험치
        long totalExp = userTotalExpInInfo + totalExpThisYear;

        // 작년까지의 누적 경험치 (총 경험치 - 올해 경험치)
        long previousYearsTotalExp = totalExp - totalExpThisYear;

        // 현재 레벨 계산
        Level currentLevel = getCurrentLevel(totalExp);
        Level nextLevel = getNextLevel(currentLevel);

        // 다음 레벨까지 남은 경험치 계산
        long nextLevelExp = nextLevel != null ? nextLevel.getRequiredExp() : 0;
        long remainingExp = nextLevelExp - totalExp;

        // 올해 경험치 max 값
        long maxExpThisYear = 9000;

        // 현재 경험치 통 및 다음 레벨 통
        long currentLevelExp = currentLevel.getRequiredExp();
        long experienceInBucket = totalExp - currentLevelExp;
        long bucketSize = nextLevelExp - currentLevelExp;

        // 응답 객체 생성
        ExpResponse response = new ExpResponse();
        response.setTotalExp(totalExp); // 총 경험치 (userInfo 총경험치 + 올해 경험치)
        response.setPreviousYearsTotalExp(previousYearsTotalExp); // 작년까지의 누적 경험치
        response.setPreviousYearsTotalExpMax(currentLevelExp); // 작년까지의 누적 경험치 max 값
        response.setExpThisYear(totalExpThisYear); // 올해 획득한 경험치
        response.setExpThisYearMax(maxExpThisYear); // 올해 획득 경험치 max 값
        response.setNextLevelName(nextLevel != null ? nextLevel.getFirestoreValue() : null); // 다음 레벨 이름 반환
        response.setRemainingExp(remainingExp > 0 ? remainingExp : 0);
        response.setBucketSize(bucketSize > 0 ? bucketSize : 0);
        response.setExperienceInBucket(experienceInBucket);

        return response;
    }

    // 현재 레벨 계산
    private Level getCurrentLevel(long totalExp) {
        Level currentLevel = Level.F1_I; // 초기값 설정
        for (Level level : Level.values()) {
            if (totalExp >= level.getRequiredExp()) {
                currentLevel = level;
            } else {
                break;
            }
        }
        return currentLevel;
    }

    // 안전한 Long 파싱
    private long parseLongOrDefault(String value, long defaultValue) {
        try {
            return Long.parseLong(value.replace(",", ""));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // 다음 레벨 계산
    private Level getNextLevel(Level currentLevel) {
        Level[] levels = Level.values();
        int currentIndex = -1;

        for (int i = 0; i < levels.length; i++) {
            if (levels[i] == currentLevel) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex != -1 && currentIndex < levels.length - 1) {
            return levels[currentIndex + 1]; // 다음 레벨 반환
        }
        return null; // 다음 레벨이 없는 경우
    }



}
