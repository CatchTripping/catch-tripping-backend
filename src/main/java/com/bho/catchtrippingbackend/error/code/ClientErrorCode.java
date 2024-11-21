package com.bho.catchtrippingbackend.error.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ClientErrorCode implements ErrorCodeModel{
    // 회원가입 관련 오류
    DUPLICATE_USERNAME(400, "DUPLICATE_USERNAME", "이미 사용 중인 사용자 이름입니다."),
    DUPLICATE_EMAIL(400, "DUPLICATE_EMAIL", "이미 사용 중인 이메일입니다."),
    INVALID_PASSWORD(400, "INVALID_PASSWORD", "비밀번호는 최소 8자, 대문자, 숫자, 특수 문자를 포함해야 합니다."),
    INVALID_EMAIL(400, "INVALID_EMAIL", "유효하지 않은 이메일 형식입니다."),
    INVALID_REQUEST(400, "INVALID_REQUEST", "유효하지 않은 요청입니다."),
    // 로그인(인증) 관련 오류
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "해당 사용자를 찾을 수 없습니다."),
    NOT_AUTHORIZED(401, "NOT_AUTHORIZED", "로그인이 필요합니다."),
    AUTHENTICATION_FAILED(401, "AUTHENTICATION_FAILED", "인증에 실패했습니다."),
    FORBIDDEN_USER_ACCESS(403, "FORBIDDEN_USER_ACCESS", "권한이 없는 사용자의 접근입니다."),
    // Board 관련 오류
    BOARD_NOT_FOUND(404, "BOARD_NOT_FOUND", "해당 게시글을 찾을 수 없습니다."),
    // BoardLike 관련 오류
    BOARD_LIKE_DUPLICATED(400, "BOARD_LIKE_DUPLICATED", "좋아요를 이미 눌렀습니다."),
    BOARD_LIKE_NOT_FOUND(404, "BOARD_LIKE_NOT_FOUND", "좋아요를 찾을 수 없습니다."),
    // 댓글 관련 오류
    COMMENT_NOT_FOUND(404, "COMMENT_NOT_FOUND", "해당 댓글을 찾을 수 없습니다."),
    COMMENT_DEPTH_EXCEEDED_ERR(400, "COMMENT_DEPTH_EXCEEDED_ERR", "댓글은 대댓글까지만 허용합니다."),
    COMMENT_ALREADY_DELETED_ERR(400, "COMMENT_ALREADY_DELETED_ERR", "이미 삭제된 댓글입니다.")
    ;

    private final int statusCode;
    private final String systemErrorCode;
    private final String errorMessage;
}
