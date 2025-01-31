package com.syp.board_back.dto.board.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentUpdateRequest {
    private String comment_content;
}
