package com.syp.board_back.dto.board.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class CommentReadResponse {
    private Long comment_id;
    private int comment_level;
    private Long comment_parentId;
    private String user_id;
    private String comment_content;
    private Date comment_postedTime;
    private byte comment_isDeleted;
}
