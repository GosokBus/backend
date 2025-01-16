package com.example.demo.userinfo.badge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class BadgeService {

    private final BadgeRepository badgeRepository;

    @Autowired
    public BadgeService(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    // 특정 유저의 모든 뱃지 정보 조회
    public List<Badge> getUserBadges(String userId) throws ExecutionException, InterruptedException {
        return badgeRepository.findByUserId(userId);
    }

    // 특정 뱃지 상태 업데이트(활성화 조건 추가 구현 필요)
    public void updateBadgeStatus(String badgeId, boolean status) throws ExecutionException, InterruptedException {
        badgeRepository.updateBadgeStatus(badgeId, status);
    }
}