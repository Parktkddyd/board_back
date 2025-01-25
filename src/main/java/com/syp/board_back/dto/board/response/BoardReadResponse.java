package com.syp.board_back.dto.board.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardReadResponse {
    private Long board_id;
    private String board_title;
    private String user_id;
    private String board_createdAt;
    private String board_viewCnt;
}
