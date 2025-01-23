package com.syp.board_back.dto.board.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardUpdateResponse {
    private Long board_id;
    private String board_title;
    private String board_content;
}
