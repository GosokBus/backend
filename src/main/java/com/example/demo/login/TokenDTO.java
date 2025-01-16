package com.example.demo.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {

    private String accessToken;
    private String refreshToken;
    private String userId;

    public TokenDTO(String newAccessToken, String newRefreshToken, String userId) {
        this.accessToken = newAccessToken;
        this.refreshToken = newRefreshToken;
        this.userId = userId;
    }

}
