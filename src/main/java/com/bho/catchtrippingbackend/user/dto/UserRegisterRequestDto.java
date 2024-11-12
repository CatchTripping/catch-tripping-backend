package com.bho.catchtrippingbackend.user.dto;

import com.bho.catchtrippingbackend.user.entity.User;
import com.bho.catchtrippingbackend.user.util.PasswordValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegisterRequestDto(
    @NotNull @Size(min = 3, max = 50) String userName,
    @NotNull @Size(min = 8) String userPassword,
    @NotNull @Email String userEmail
) {
    public void validatePassword() {
        if (!PasswordValidator.isValid(userPassword)) {
            throw new IllegalArgumentException("비밀번호는 최소 8자, 대문자, 숫자, 특수 문자를 포함해야 합니다.");
        }
    }
    public User toEntity(String encodedPassword) {
        return User.builder()
                .userName(userName)
                .userPassword(encodedPassword)
                .userEmail(userEmail)
                .build();
    }
}
