package com.bho.catchtrippingbackend.users.model.service;


import com.bho.catchtrippingbackend.users.model.UsersDto;
import com.bho.catchtrippingbackend.users.model.dao.UsersDao;
import com.bho.catchtrippingbackend.users.util.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersDao usersDao;
    private final PasswordEncoder passwordEncoder;

    public boolean isUsernameAvailable(String userName) {
        return usersDao.findUserByUsername(userName) == null;
    }

    public boolean isEmailAvailable(String userEmail) {
        return usersDao.findUserByEmail(userEmail) == null;
    }

    @Transactional
    public void registerUser(UsersDto user) {
        if (!isEmailAvailable(user.getUserEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (!PasswordValidator.isValid(user.getUserPassword())) {
            throw new IllegalArgumentException("비밀번호는 최소 8지, 대문자, 숫자, 특수 문자를 포함해야 합니다.");
        }
        
        // 비밀번호 암호화 후 저장
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        usersDao.createUser(user);
    }

    public UsersDto findUserByUsername(String userName) {
        return usersDao.findUserByUsername(userName);
    }
}
