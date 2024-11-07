package com.bho.catchtrippingbackend.users.model.service;


import com.bho.catchtrippingbackend.users.model.UsersDto;
import com.bho.catchtrippingbackend.users.model.dao.UsersDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersDao usersDao;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UsersDto user) {
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        usersDao.createUser(user);
    }

    public UsersDto findUserByUsername(String userName) {
        return usersDao.findUserByUsername(userName);
    }
}
