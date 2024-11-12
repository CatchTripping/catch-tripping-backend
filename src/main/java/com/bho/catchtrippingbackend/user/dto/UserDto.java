package com.bho.catchtrippingbackend.user.dto;

import com.bho.catchtrippingbackend.user.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record UserDto(
        String userName,
        String userEmail,
        String profileImage,
        Collection<? extends GrantedAuthority> authorities
) {
    public static UserDto fromEntity(User user, Collection<? extends GrantedAuthority> authorities) {
        return new UserDto(
                user.getUserName(),
                user.getUserEmail(),
                user.getProfileImage(),
                authorities
        );
    }
}
