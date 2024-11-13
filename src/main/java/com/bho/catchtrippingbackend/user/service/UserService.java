package com.bho.catchtrippingbackend.user.service;


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

    public boolean isUsernameAvailable(String userName) {
        return userDao.countUsersByUserName(userName) == 0;
    }

    public boolean isEmailAvailable(String userEmail) {
        return userDao.countUsersByEmail(userEmail) == 0;
    }

    @Transactional
    public UserRegisterResponseDto registerUser(UserRegisterRequestDto request) {
        // 비밀번호 유효성 검사
        request.validatePassword();

        // 사용자 이름 중복 체크
        if (!isUsernameAvailable(request.userName())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        // 사용자 이메일 중복 체크
        if (!isEmailAvailable(request.userEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 비밀번호 암호화 후 저장
        String encryptedPassword = passwordEncoder.encode(request.userPassword());

        // 엔티티 생성
        User user = request.toEntity(encryptedPassword);
//        User user = User.builder()
//                .userName(request.userName())
//                .userEmail(request.userEmail())
//                .userPassword(encryptedPassword)
//                .build();

        userDao.createUser(user);

        return new UserRegisterResponseDto(user.getUserId());
    }

    public User findUserByUsername(String userName) {
        return userDao.findUserByUsername(userName);
    }
}
