package com.bho.catchtrippingbackend.user.dto;

import com.bho.catchtrippingbackend.error.SystemException;
import com.bho.catchtrippingbackend.error.code.ClientErrorCode;
import com.bho.catchtrippingbackend.user.entity.User;
import com.bho.catchtrippingbackend.user.util.PasswordValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.regex.Pattern;

public record UserRegisterRequestDto(
    @NotNull @Size(min = 3, max = 50) String userName,
    @NotNull @Size(min = 8) String userPassword,
    @NotNull String userEmail
) {
    public void validatePassword() {
        if (!PasswordValidator.isValid(userPassword)) {
            throw new SystemException(ClientErrorCode.INVALID_PASSWORD);
        }
    }

    public void validateEmail() {
        // 이메일 형식 검증 (정규식으로 간단한 예시)
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (userEmail == null || !Pattern.matches(emailPattern, userEmail)) {
            throw new SystemException(ClientErrorCode.INVALID_EMAIL);  // INVALID_EMAIL 에러 코드 사용
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
