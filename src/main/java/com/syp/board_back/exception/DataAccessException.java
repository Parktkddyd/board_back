package com.syp.board_back.exception;

import com.syp.board_back.dto.response.common.ResponseCode;

public class DataAccessException extends BaseException {
    public DataAccessException(ResponseCode responseCode) {
        super(responseCode);
    }
}
