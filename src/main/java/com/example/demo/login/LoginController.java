package com.example.demo.login;

import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.jwt.RefreshToken;
import com.example.demo.jwt.RefreshTokenRepository;
import com.example.demo.userinfo.UserInfo;
import com.example.demo.userinfo.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserInfoService userInfoService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest, HttpServletRequest request) throws ExecutionException, InterruptedException {

        UserInfo userInfo = userInfoService.login(loginRequest);

        if(userInfo == null || !userInfo.getPassword().equals(loginRequest.getPassword())) {
            throw new IllegalArgumentException("아이디 혹은 비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(userInfo.getUserId(), userInfo.getUserName());
        String refreshToken = jwtTokenProvider.createRefreshToken(userInfo.getUserId(), userInfo.getUserName());



        RefreshToken existingToken = refreshTokenRepository.findByUserId(userInfo.getUserId());
        if (existingToken != null) {
            existingToken.updateRefreshToken(refreshToken);
            refreshTokenRepository.save(existingToken);
        } else {
            refreshTokenRepository.save(new RefreshToken(refreshToken, userInfo.getUserId()));
        }


        return ResponseEntity.ok(new TokenDTO(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refresh(@RequestBody RefreshTokenDTO refreshToken) throws ExecutionException, InterruptedException {


        // 만료되었거나 위조된 토큰이면 예외 발생
        if(!jwtTokenProvider.validateToken(refreshToken.getRefreshToken())) {
            throw new IllegalArgumentException("리프레시 토큰이 유효하지 않습니다.");
        }

        // DB에 저장된 refreshToken 찾기, 토큰 없으면 도난되었거나 이미 사용된 토큰
        RefreshToken savedRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken.getRefreshToken());
        if (savedRefreshToken == null) {
            throw new IllegalArgumentException("리프레시 토큰을 찾을 수 없습니다.");
        }

        UserInfo userInfo = userInfoService.getMyInfo(savedRefreshToken.getUserId());
        if (userInfo == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(userInfo.getUserId() ,userInfo.getUserName());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(userInfo.getUserId(), userInfo.getUserName());

        // DB에 저장된 refresh 토큰을 새로 생성된 토큰으로 교체
        savedRefreshToken.updateRefreshToken(newRefreshToken);
        refreshTokenRepository.save(savedRefreshToken);

        // 새로운 access 토큰과 refresh 토큰 전달
        return ResponseEntity.ok(new TokenDTO(newAccessToken, newRefreshToken));
    }

}
