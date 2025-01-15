package com.syp.board_back.dto.response.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {
    private ApiHeader header;
    private T data;
    private String message;

    private ApiResponse(ApiHeader header, T data, String message) {
        this.header = header;
        this.data = data;
        this.message = message;
    }

    private static <T> ApiResponse<T> create(ResponseCode responseCode, T data, String message) {
        return new ApiResponse<>(
                new ApiHeader(responseCode.getHttpStatusCode(), responseCode.getCustomStatusCode()),
                data,
                message
        );
    }

    public static <T> ApiResponse<T> success(ResponseCode responseCode, T data, String message) {
        return create(responseCode, data, message);
    }

    public static <T> ApiResponse<T> fail(ResponseCode responseCode, T data, String message) {
        return create(responseCode, data, message);
    }
}