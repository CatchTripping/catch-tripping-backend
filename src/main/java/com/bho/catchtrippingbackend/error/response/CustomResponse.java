package com.bho.catchtrippingbackend.error.response;

import com.bho.catchtrippingbackend.error.code.ErrorCodeModel;

public record CustomResponse<T> (
    int status,
    String code,
    String description,
    T data
) {
    // 실패 응답
    public static CustomResponse<Void> error(ErrorCodeModel errorCode) {
        return new CustomResponse<>(
                errorCode.getStatusCode(),
                errorCode.getSystemErrorCode(),
                errorCode.getErrorMessage(),
                null
        );
    }

    // 성공 응답 (데이터와 기본 메시지 포함)
    public static <T> CustomResponse<T> success(T data) {
        return new CustomResponse<>(
                200,
                "SUCCESS",
                "요청이 성공적으로 처리되었습니다.",
                data
        );
    }

    // 성공 응답 (데이터와 사용자 지정 메시지 포함)
    public static <T> CustomResponse<T> success(T data, String message) {
        return new CustomResponse<>(
                200,
                "SUCCESS",
                message,
                data
        );
    }

    // 성공 응답 (기본 메시지 포함, 데이터 없음)
    public static CustomResponse<Void> success() {
        return new CustomResponse<>(
                200,
                "SUCCESS",
                "요청이 성공적으로 처리되었습니다.",
                null
        );
    }
}
