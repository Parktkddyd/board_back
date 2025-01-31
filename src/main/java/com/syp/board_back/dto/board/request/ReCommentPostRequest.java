package com.syp.board_back.dto.board.request;

import lombok.Getter;

@Getter
public class ReCommentPostRequest extends CommentPostRequest {
    private Long parent_id;

    public ReCommentPostRequest(String comment_content, Long parent_id) {
        super(comment_content);
        this.parent_id = parent_id;
    }
}
