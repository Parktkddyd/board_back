package com.syp.board_back.dto.board.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardDeleteResponse {
    private Long board_id;
    private byte isDeleted;
}
