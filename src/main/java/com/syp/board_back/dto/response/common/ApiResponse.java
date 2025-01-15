package com.syp.board_back.dto.response.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {
    private ApiHeader header;
    private T data;
    private String message;

    private static final int SUCCESS = 200;

    private ApiResponse(ApiHeader header, T data, String message) {
        this.header = header;
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<T>(new ApiHeader(SUCCESS, "SUCCESS"), data, message);
    }

    public static <T> ApiResponse<T> fail(ResponseCode responseCode, T data) {
        return new ApiResponse<T>(new ApiHeader(responseCode.getHttpStatusCode(), responseCode.getErrorCode()), data, responseCode.getMessage());
    }
}