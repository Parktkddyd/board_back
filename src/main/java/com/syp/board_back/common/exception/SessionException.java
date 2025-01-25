package com.syp.board_back.common.exception;

import com.syp.board_back.dto.common.response.ResponseCode;

public class SessionException extends BaseException {
    public SessionException(ResponseCode responseCode) {
        super(responseCode);
    }
}
