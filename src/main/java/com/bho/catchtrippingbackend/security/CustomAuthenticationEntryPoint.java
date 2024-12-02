package com.bho.catchtrippingbackend.security;

import com.bho.catchtrippingbackend.error.code.ClientErrorCode;
import com.bho.catchtrippingbackend.error.response.CustomResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // 클래스 선언 내부에 Logger 설정
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json");

        // 발생한 예외 메시지를 로그로 출력하여 확인
        logger.error("Authentication exception: {}", authException.getMessage());


        if (authException.getMessage().contains("Bad credentials")) {
            // 비밀번호가 맞지 않는 경우
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            CustomResponse<Void> errorResponse = CustomResponse.error(ClientErrorCode.USER_NOT_FOUND);
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } else {
            // 그 외 인증 문제의 경우
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            CustomResponse<Void> errorResponse = CustomResponse.error(ClientErrorCode.NOT_AUTHORIZED);
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }
    }
}