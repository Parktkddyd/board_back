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
    //로그인 성공
    USER_LOGIN_SUCCESS(HttpStatus.OK, "ACCOUNT-LOGIN-200", "로그인 성공"),
    //아이디 오류
    USER_LOGIN_ID_FAIL(HttpStatus.BAD_REQUEST, "ACCOUNT-LOGIN-400-1", "아이디 오류"),
    //비밀번호 오류
    USER_LOGIN_PASS_FAIL(HttpStatus.BAD_REQUEST, "ACCOUNT-LOGIN-400-2", "비밀번호 오류"),
    //검증값 오류
    USER_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "ACCOUNT-VAL-400", "유효성 검증 실패"),
    //세션 인증 실패
    USER_NOT_ACCESS(HttpStatus.FORBIDDEN, "USER-ACCESS-403", "세션이 없는 유저의 접근"),
    //공통 - 데이터 베이스 오류
    DB_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "D-001", "서버에 오류가 발생하였습니다."),
    DB_DUPLICATE_ERROR(HttpStatus.CONFLICT, "D-002", "중복된 값 입니다.");

    private final HttpStatus httpStatus;
    private final String customStatusCode;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
