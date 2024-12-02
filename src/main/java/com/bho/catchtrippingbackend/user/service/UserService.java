package com.bho.catchtrippingbackend.user.service;

import com.amazonaws.HttpMethod;
import com.bho.catchtrippingbackend.error.SystemException;
import com.bho.catchtrippingbackend.error.code.ClientErrorCode;
import com.bho.catchtrippingbackend.s3.service.S3Service;
import com.bho.catchtrippingbackend.user.dto.UserRegisterRequestDto;
import com.bho.catchtrippingbackend.user.dto.UserRegisterResponseDto;
import com.bho.catchtrippingbackend.user.entity.User;
import com.bho.catchtrippingbackend.user.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;

    public boolean isUsernameAvailable(String userName) {
        return userDao.countUsersByUserName(userName) == 0;
    }

    public boolean isEmailAvailable(String userEmail) {
        return userDao.countUsersByEmail(userEmail) == 0;
    }

    @Transactional
    public UserRegisterResponseDto registerUser(UserRegisterRequestDto request) {
        // 비밀번호 이메일 형식 체크
        request.validatePassword();
        request.validateEmail();

        // 사용자 이름 중복 체크
        if (!isUsernameAvailable(request.userName())) {
            throw new SystemException(ClientErrorCode.DUPLICATE_USERNAME);
        }

        // 사용자 이메일 중복 체크
        if (!isEmailAvailable(request.userEmail())) {
            throw new SystemException(ClientErrorCode.DUPLICATE_EMAIL);
        }

        // 비밀번호 암호화 후 저장
        String encryptedPassword = passwordEncoder.encode(request.userPassword());

        // 엔티티 생성
        User user = request.toEntity(encryptedPassword);
        userDao.createUser(user);

        return new UserRegisterResponseDto(user.getUserId());
    }

    public User findUserByUsername(String userName) {
        User user = userDao.findUserByUsername(userName);
        if (user == null) {
            throw new SystemException(ClientErrorCode.USER_NOT_FOUND);
        }

        return user;
    }

    @Transactional
    public void uploadProfileImage(Long userId, String tempImageKey) {
        User user = getUserById(userId);

        // 이전 프로필 이미지 삭제 (필요에 따라)
        String previousImageKey = user.getProfileImage();
        if (previousImageKey != null) {
            s3Service.deleteObject(previousImageKey);
        }

        // 임시 이미지 키를 최종 이미지 키로 변경
        String newImageKey = tempImageKey.replace("profile-images/temp", "profile-images/" + userId);

        // S3에서 이미지 이동
        s3Service.moveObject(tempImageKey, newImageKey);

        // 사용자 엔터티 업데이트
        user.setProfileImage(newImageKey);
        userDao.updateUserProfileImage(user);
    }

    @Transactional
    public void deleteProfileImage(Long userId) {
        User user = getUserById(userId);
        String imageKey = user.getProfileImage();
        if (imageKey != null) {
            s3Service.deleteObject(imageKey);
            user.setProfileImage(null);
            userDao.updateUserProfileImage(user);
        }
    }

    public String getProfileImageUrl(Long userId) {
        User user = getUserById(userId);
        String imageKey = user.getProfileImage();
        if (imageKey != null) {
            return s3Service.generatePresignedUrl(imageKey, HttpMethod.GET);
        }
        return null;
    }

    private User getUserById(Long userId) {
        User user = userDao.findUserById(userId);
        if (user == null) {
            throw new SystemException(ClientErrorCode.USER_NOT_FOUND);
        }
        return user;
    }
}
