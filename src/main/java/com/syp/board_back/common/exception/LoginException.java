package com.syp.board_back.common.exception;

import com.syp.board_back.dto.common.response.ResponseCode;

public class LoginException extends BaseException {
    public LoginException(ResponseCode responseCode) {
        super(responseCode);
    }
}
