package com.example.demo.expthisyear;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpResponse {
    private long totalExp; // 총 경험치
    private long previousYearsTotalExp; // 작년까지의 누적 경험치
    private long previousYearsTotalExpMax; // 작년까지의 누적 경험치 max 값 (현재 레벨의 요구 경험치)
    private long expThisYear; // 올해 획득한 경험치
    private long expThisYearMax; // 올해 획득 경험치 max 값 (9000)
    private String nextLevelName; // 다음 레벨 이름
    private long remainingExp; // 다음 레벨까지 남은 경험치
    private long bucketSize; // 현재 레벨에서 다음 레벨까지의 경험치 통 크기
    private long experienceInBucket; // 현재 레벨에서 누적된 경험치 양
}