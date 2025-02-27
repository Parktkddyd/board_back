package com.syp.board_back.dto.common.response;

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
    //로그아웃 성공
    USER_LOGOUT_SUCCESS(HttpStatus.OK, "ACCOUNT-LOGOUT-200", "로그아웃 성공"),
    //게시글 작성 성공
    POST_SUCCESS(HttpStatus.CREATED, "POST-201", "게시글(댓글) 작성 성공"),
    //게시글 수정 성공
    UPDATE_SUCCESS(HttpStatus.OK, "UPDATE-200", "게시글(댓글) 수정 성공"),
    //게시글 삭제 성공
    DELETE_SUCCESS(HttpStatus.OK, "DELETE-200", "게시글(댓글) 삭제 성공"),
    //게시글 상세 조회 성공
    READ_DETAIL_SUCCESS(HttpStatus.OK, "READ-200-1", "게시글 상세 조회 성공"),
    //게시글 전체 조회 성공
    READ_LIST_SUCCESS(HttpStatus.OK, "READ-200-2", "게시글 전체 조회 성공"),
    //게시글 찾을 수 없음
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOTFOUND-404", "게시글 또는 댓글 찾을 수 없음"),
    //공통 - 데이터 베이스 오류
    DB_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "D-001", "서버에 오류가 발생하였습니다."),
    DB_DUPLICATE_ERROR(HttpStatus.CONFLICT, "D-002", "중복된 값 입니다."),
    //공통 - 세션 인증
    //세션 인증 실패
    USER_NOT_ACCESS(HttpStatus.FORBIDDEN, "USER-ACCESS-403", "접근 권한이 없습니다."),
    //세션 인증 성공
    USER_ACCESS_OK(HttpStatus.OK, "USER-ACCESS-200", "인증 성공"),
    //공통 - 유저 입력값 검증
    //검증값 오류
    USER_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "USER-VAL-400", "유효성 검증 실패"),
    //페이징 파라미터 오류
    PAGE_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "PAGE-400", "페이지 파라미터 오류");

    private final HttpStatus httpStatus;
    private final String customStatusCode;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
