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
    UNEXPECTED_ERROR(500, "UNEXPECTED_ERROR", "예상치 못한 서버 오류가 발생했습니다."),
    EXTERNAL_API_ERROR(503, "EXTERNAL_API_ERROR", "외부 API 호출에 실패했습니다."),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "서버 내부에서 오류가 발생했습니다."),
    S3_CONNECTION_FAILED(500, "S3_CONNECTION_FAILED", "AWS S3 연결에 실패했습니다."),
    S3_OPERATION_FAILED(500, "S3_OPERATION_FAILED", "AWS S3 작업 수행 중 오류가 발생했습니다."),
    CONFIGURATION_ERROR(500, "CONFIGURATION_ERROR", "서버 구성 중 오류가 발생했습니다."),
    IMAGE_UPLOAD_FAILED(500, "IMAGE_UPLOAD_FAILED", "이미지 업로드에 실패했습니다."),
    IMAGE_DELETE_FAILED(500, "IMAGE_DELETE_FAILED", "이미지 삭제에 실패했습니다.")
    ;
    private final int statusCode;
    private final String systemErrorCode;
    private final String errorMessage;
}
