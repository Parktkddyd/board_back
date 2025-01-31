package com.syp.board_back.dto.board.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentUpdateResponse {
    private Long comment_id;
    private String comment_content;
}
