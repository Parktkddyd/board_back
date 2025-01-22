package com.syp.board_back.domain.board;

import lombok.Data;

@Data
public class BoardContent {
    private Long content_id;
    private Long board_id;
    private String board_content;

    public BoardContent(Long board_id, String board_content) {
        this.board_id = board_id;
        this.board_content = board_content;
    }
}
