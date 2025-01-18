package com.example.demo.expthisyear.exprecent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecentExp {
    private String createdAt; // 생성 시간
    private String exp;       // 경험치
    private String quest;     // 퀘스트
}
