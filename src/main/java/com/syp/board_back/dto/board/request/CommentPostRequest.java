package com.syp.board_back.dto.board.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentPostRequest {
    private String comment_content;
}
