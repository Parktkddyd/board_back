package com.syp.board_back.common.exception;

import com.syp.board_back.dto.common.response.ResponseCode;

public class BoardException extends BaseException {
    public BoardException(ResponseCode responseCode) {
        super(responseCode);
    }
}
