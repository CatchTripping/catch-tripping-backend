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

    public static <T> CustomResponse<T> success(T data) {
        return new CustomResponse<>(
                200,
                "SUCCESS",
                "요청이 성공적으로 처리되었습니다.",
                data
        );
    }

    public static CustomResponse<Void> success() {
        return new CustomResponse<>(
                200,
                "SUCCESS",
                "요청이 성공적으로 처리되었습니다.",
                null
        );
    }
}
