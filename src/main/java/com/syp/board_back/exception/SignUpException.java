package com.syp.board_back.exception;

import com.syp.board_back.dto.response.common.ResponseCode;
import com.syp.board_back.exception.common.BaseException;

public class SignUpException extends BaseException {
    public SignUpException(ResponseCode responseCode) {
        super(responseCode);
    }
}
