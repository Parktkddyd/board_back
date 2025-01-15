package com.syp.board_back.dto.response.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    //회원가입
    USER_CREATE_SUCCESS(HttpStatus.CREATED, "0", "회원가입 성공"),

    //공통 - 서버오류 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Z-001", "서버에 오류가 발생하였습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    public int getHttpStatusCode(){
        return httpStatus.value();
    }
}
