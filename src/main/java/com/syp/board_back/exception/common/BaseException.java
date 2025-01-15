package com.syp.board_back.exception.common;

import com.syp.board_back.dto.response.common.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BaseException extends RuntimeException{
    private final ResponseCode responseCode;

    @Override
    public String getMessage(){
        return responseCode.getMessage();
    }
}
