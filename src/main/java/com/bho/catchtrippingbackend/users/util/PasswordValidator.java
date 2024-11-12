package com.bho.catchtrippingbackend.users.util;

public class PasswordValidator {

    private PasswordValidator() {} // 인스턴스화 방지

    /**
     * 비밀번호 유효성 검사
     * - 최소 8자, 대문자 포함, 숫자 포함, 특수 문자 포함
     * @param password 검증할 비밀번호
     * @return 유효한 경우 true, 그렇지 않으면 false
     */
    public static boolean isValid(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#$%^&*].*");
    }
}
