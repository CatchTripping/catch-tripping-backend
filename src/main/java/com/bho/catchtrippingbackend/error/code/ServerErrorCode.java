package com.bho.catchtrippingbackend.error.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ServerErrorCode implements ErrorCodeModel{
    DATABASE_ERROR(500, "DATABASE_ERROR", "데이터베이스 연결에 실패하였습니다."),
    FILE_STORAGE_ERROR(500, "FILE_STORAGE_ERROR", "파일 업로드에 실패하였습니다."),
    ENCRYPTION_ERROR(500, "ENCRYPTION_ERROR", "암호화에 실패하였습니다."),
    DECRYPTION_ERROR(500, "DECRYPTION_ERROR", "복호화에 실패하였습니다."),
    EXTERNAL_SERVICE_ERROR(502, "EXTERNAL_SERVICE_ERROR", "외부 서비스와의 통신에 실패하였습니다."),
    UNEXPECTED_ERROR(500, "UNEXPECTED_ERROR", "예상치 못한 서버 오류가 발생했습니다.");

    private final int statusCode;
    private final String systemErrorCode;
    private final String errorMessage;
}
