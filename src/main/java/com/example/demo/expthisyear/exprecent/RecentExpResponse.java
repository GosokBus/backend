package com.example.demo.expthisyear.exprecent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecentExpResponse {
    private String exp;
    private String quest;
    private String createdAt; // ISO-8601 형식
}
