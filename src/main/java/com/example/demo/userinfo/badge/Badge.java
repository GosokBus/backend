package com.example.demo.userinfo.badge;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Badge {
    private String badgeId;
    private boolean status; // 활성화 여부

    public Badge(String badgeId, boolean status) {
        this.badgeId = badgeId;
        this.status = status;
    }
}