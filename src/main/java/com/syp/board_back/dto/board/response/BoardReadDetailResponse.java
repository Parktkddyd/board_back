package com.syp.board_back.dto.board.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardReadDetailResponse extends BoardReadResponse {
    private String content;
    private int page;

    public BoardReadDetailResponse(Long board_id, String board_title, String user_id,
                                   String board_createdAt, String board_viewCnt, String content) {
        super(board_id, board_title, user_id, board_createdAt, board_viewCnt);
        this.content = content;
    }
}
