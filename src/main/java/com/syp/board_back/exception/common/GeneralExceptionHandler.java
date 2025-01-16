package com.syp.board_back.exception.common;

import com.syp.board_back.dto.response.common.ApiResponse;
import com.syp.board_back.dto.response.common.ResponseCode;
import com.syp.board_back.exception.DataAccessException;
import com.syp.board_back.exception.LoginException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Map<String, String>> handlerValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ApiResponse.fail(ResponseCode.USER_CREATE_VALIDATION_ERROR, errors,
                ResponseCode.USER_CREATE_VALIDATION_ERROR.getMessage());
    }

    @ExceptionHandler(LoginException.class)
    public ApiResponse<Void> handlerLoginException(LoginException e) {
        return ApiResponse.fail(e.getResponseCode(), null, e.getResponseCode().getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    public ApiResponse<Void> handlerDataAccessException(DataAccessException e) {
        return ApiResponse.fail(e.getResponseCode(), null, e.getResponseCode().getMessage());
    }

}
