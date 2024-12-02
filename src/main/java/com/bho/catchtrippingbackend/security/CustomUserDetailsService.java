package com.bho.catchtrippingbackend.security;

import com.bho.catchtrippingbackend.user.entity.User;
import com.bho.catchtrippingbackend.user.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid credential");
        }

        return new CustomUserDetails(
                user.getUserId(),
                user.getUserName(),
                user.getUserPassword(),
                List.of(() -> "ROLE_USER")
        );

    }
}
