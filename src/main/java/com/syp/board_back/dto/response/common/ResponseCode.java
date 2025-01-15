package com.syp.board_back.dto.response.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    //회원가입
    USER_CREATE_SUCCESS(HttpStatus.CREATED, "ACCOUNT-SIGNUP-201", "회원가입 성공"),
    //중복체크 성공
    ID_DUP_CHECK_SUCCESS(HttpStatus.OK, "ACCOUNT-DUP-200", "검색 성공"),
    //검증값 오류
    USER_CREATE_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "ACCOUNT-VAL-400", "유효성 검증 실패"),
    //공통 - 서버오류 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Z-001", "서버에 오류가 발생하였습니다.");

    private final HttpStatus httpStatus;
    private final String customStatusCode;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
