package com.bho.catchtrippingbackend.attractions.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttractionCustomResponse<T> {
    private int status;
    private String code;
    private String message;
    private T data;

    // 성공 응답 생성 메서드
    public static <T> AttractionCustomResponse<T> success(T data) {
        AttractionCustomResponse<T> response = new AttractionCustomResponse<>();
        response.setStatus(200);
        response.setCode("SUCCESS");
        response.setMessage("요청이 성공적으로 처리되었습니다.");
        response.setData(data);
        return response;
    }

    // 성공 응답 (커스텀 메시지)
    public static <T> AttractionCustomResponse<T> success(T data, String message) {
        AttractionCustomResponse<T> response = new AttractionCustomResponse<>();
        response.setStatus(200);
        response.setCode("SUCCESS");
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    // 실패 응답 생성 메서드
    public static <T> AttractionCustomResponse<T> error(int status, String code, String message) {
        AttractionCustomResponse<T> response = new AttractionCustomResponse<>();
        response.setStatus(status);
        response.setCode(code);
        response.setMessage(message);
        response.setData(null);
        return response;
    }
}
