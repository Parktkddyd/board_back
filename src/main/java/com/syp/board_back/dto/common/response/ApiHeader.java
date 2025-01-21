package com.syp.board_back.dto.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class ApiHeader {
    private int statusCode;
    private String customStatusCode;
}

