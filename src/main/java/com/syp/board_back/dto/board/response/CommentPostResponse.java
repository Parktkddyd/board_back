package com.syp.board_back.dto.board.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentPostResponse {
    private Long board_id;
    private Long comment_id;
    private String user_id;
}
