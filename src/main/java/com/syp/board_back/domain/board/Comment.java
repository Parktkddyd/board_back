package com.syp.board_back.domain.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Comment {
    private Long comment_id;
    private Long board_id;
    private int comment_group;
    private int comment_level;
    private int comment_groupOrder;
    private int comment_childCount;
    private Integer comment_parentId;
    private String user_id;
    private String comment_content;
    private Date comment_createdAt;
    private Date comment_updatedAt;
    private byte comment_isDeleted;

    public Comment(Long board_id, String user_id, String comment_content) {
        this.board_id = board_id;
        this.user_id = user_id;
        this.comment_content = comment_content;
    }
}
