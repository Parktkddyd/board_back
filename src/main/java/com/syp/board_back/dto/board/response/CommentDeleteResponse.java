package com.syp.board_back.dto.board.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDeleteResponse {
    private Long comment_id;
    private byte comment_isDeleted;
}
