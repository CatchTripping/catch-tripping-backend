package com.bho.catchtrippingbackend.security;

import com.bho.catchtrippingbackend.users.model.UsersDto;
import com.bho.catchtrippingbackend.users.model.dao.UsersDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersDao usersDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersDto user = usersDao.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid credential");
        }

        return User.builder()
                .username(user.getUserName())
                .password(user.getUserPassword())
                .roles("USER")
                .build();
    }
}
