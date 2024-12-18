package com.bho.catchtrippingbackend.error;

import com.bho.catchtrippingbackend.error.code.ServerErrorCode;
import com.bho.catchtrippingbackend.error.response.CustomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * SystemException 처리
     * 사용자가 정의한 예외에 대해 에러 코드와 메시지를 포함한 응답을 반환합니다.
     */
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<CustomResponse<Void>> handleSystemException(SystemException e) {
        // 예외 로그 추가
        log.error("SystemException 발생: {}, Code: {}", e.getMessage(), e.getErrorCode().getSystemErrorCode(), e);


        return ResponseEntity
                .status(e.getErrorCode().getStatusCode())
                .body(CustomResponse.error(e.getErrorCode()));
    }

    /**
     * 예상치 못한 일반 예외 처리
     * 기타 예외에 대해 INTERNAL_SERVER_ERROR 응답을 반환합니다.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse<Void>> handleException(Exception e) {
        // 예외 로그 추가
        log.error("Unexpected Exception 발생: {}", e.getMessage(), e);

        // 서버 오류로 처리하되, 에러 코드 및 메시지를 설정
        return ResponseEntity
                .status(ServerErrorCode.UNEXPECTED_ERROR.getStatusCode())
                .body(CustomResponse.error(ServerErrorCode.UNEXPECTED_ERROR));
    }

//    @Override
//    protected ResponseEntity<Object> handleExceptionInternal(
//            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request){
//
//    }
}
