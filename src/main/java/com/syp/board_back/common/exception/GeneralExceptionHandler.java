package com.syp.board_back.common.exception;

import com.syp.board_back.dto.common.response.ApiResponse;
import com.syp.board_back.dto.common.response.ResponseCode;
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

        return ApiResponse.fail(ResponseCode.USER_VALIDATION_ERROR, errors,
                ResponseCode.USER_VALIDATION_ERROR.getMessage());
    }

    @ExceptionHandler(LoginException.class)
    public ApiResponse<Void> handlerLoginException(LoginException e) {
        return ApiResponse.fail(e.getResponseCode(), null, e.getResponseCode().getMessage());
    }

    @ExceptionHandler(DatabaseException.class)
    public ApiResponse<Void> handlerDataAccessException(DatabaseException e) {
        return ApiResponse.fail(e.getResponseCode(), null, e.getResponseCode().getMessage());
    }

    @ExceptionHandler(BoardException.class)
    public ApiResponse<Void> handlerBoardException(BoardException e) {
        return ApiResponse.fail(e.getResponseCode(), null, e.getResponseCode().getMessage());
    }

    @ExceptionHandler(SessionException.class)
    public ApiResponse<Void> handlerSessionException(SessionException e) {
        return ApiResponse.fail(e.getResponseCode(), null, e.getResponseCode().getMessage());
    }
}
