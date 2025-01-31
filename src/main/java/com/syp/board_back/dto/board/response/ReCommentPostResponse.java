package com.syp.board_back.dto.board.response;

import lombok.Getter;

@Getter
public class ReCommentPostResponse extends CommentPostResponse {
    private Long parent_id;

    public ReCommentPostResponse(Long board_id, Long comment_id, String user_id, Long parent_id) {
        super(board_id, comment_id, user_id);
        this.parent_id = parent_id;
    }
}
