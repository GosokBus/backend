package com.example.demo.userinfo.avatar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;

    @Autowired
    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    // 유저 아바타 조회
    public Avatar getUserAvatar(String userId) throws Exception {
        Avatar avatar = avatarRepository.findByUserId(userId);
        if (avatar == null) {
            throw new Exception("Avatar not found for userId: " + userId);
        }
        return avatar;
    }

    // 유저의 아바타 ID 조회
    public String getAvatarId(String userId) throws Exception {
        Avatar avatar = avatarRepository.findByUserId(userId);
        return avatar != null ? avatar.getAvatarId() : null;
    }

    // 유저 아바타 업데이트
    public void updateUserAvatar(String userId, String avatarId) throws Exception {
        Avatar avatar = avatarRepository.findByUserId(userId);

        if (avatar == null) {
            avatar = new Avatar(userId, avatarId);
        } else {
            avatar.setAvatarId(avatarId);
        }

        avatarRepository.updateAvatar(userId, avatar.getAvatarId());
    }
}
