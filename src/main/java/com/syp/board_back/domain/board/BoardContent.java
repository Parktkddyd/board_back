package com.syp.board_back.domain.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardContent {
    private Long content_id;
    private Long board_id;
    private String board_content;

    //게시글 저장을 위한 생성자 추가
    public BoardContent(Long board_id, String board_content) {
        this.board_id = board_id;
        this.board_content = board_content;
    }
}
