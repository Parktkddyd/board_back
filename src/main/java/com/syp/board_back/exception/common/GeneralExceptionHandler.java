package com.syp.board_back.exception.common;

import com.syp.board_back.dto.response.common.ApiResponse;
import com.syp.board_back.exception.SignUpException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(SignUpException.class)
    public ApiResponse<Void> handlerSignupException(SignUpException e){
        return ApiResponse.fail(e.getResponseCode(), null);
    }
}
