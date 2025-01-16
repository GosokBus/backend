package com.example.demo.userinfo.badge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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

    // 특정 유저의 3개 뱃지 중 status가 true인 것 우선 가져오고 부족하면 false인 것 추가
    public List<Badge> getTopThreeBadges(String userId) throws Exception {
        List<Badge> badges = badgeRepository.findByUserId(userId);

        // status가 true인 뱃지를 우선 필터링
        List<Badge> trueBadges = badges.stream()
                .filter(Badge::isStatus) // status가 true인 것
                .collect(Collectors.toList());

        // 부족한 경우 false인 뱃지 추가
        if (trueBadges.size() < 3) {
            List<Badge> falseBadges = badges.stream()
                    .filter(badge -> !badge.isStatus()) // status가 false인 것
                    .collect(Collectors.toList());
            // trueBadges 리스트에 부족한 만큼 falseBadges 추가
            trueBadges.addAll(falseBadges.stream()
                    .limit(3 - trueBadges.size()) // 필요한 개수만 추가
                    .collect(Collectors.toList()));
        }

        // 상위 3개만 반환
        return trueBadges.stream()
                .limit(3)
                .collect(Collectors.toList());
    }
}