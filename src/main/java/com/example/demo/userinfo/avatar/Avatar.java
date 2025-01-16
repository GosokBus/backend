package com.example.demo.userinfo.avatar;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Avatar {

    private String userId;
    private String avatarId;

    public Avatar(String userId, String avatarId) {
        this.userId = userId;
        this.avatarId = avatarId;
    }
}
