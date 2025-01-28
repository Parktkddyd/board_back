package com.syp.board_back.common.exception;

import com.syp.board_back.dto.common.response.ResponseCode;

public class PageException extends BaseException {
    public PageException(ResponseCode responseCode) {
        super(responseCode);
    }
}
