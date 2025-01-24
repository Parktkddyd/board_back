package com.syp.board_back.common.exception;

import com.syp.board_back.dto.common.response.ResponseCode;

public class DatabaseException extends BaseException {
    public DatabaseException(ResponseCode responseCode) {
        super(responseCode);
    }
}
