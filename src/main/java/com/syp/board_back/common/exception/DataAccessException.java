package com.syp.board_back.common.exception;

import com.syp.board_back.dto.common.response.ResponseCode;

public class DataAccessException extends BaseException {
    public DataAccessException(ResponseCode responseCode) {
        super(responseCode);
    }
}
