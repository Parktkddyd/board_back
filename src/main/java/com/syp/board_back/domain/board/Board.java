package com.syp.board_back.domain.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class Board {
    private Long board_id;
    private String board_title;
    private String user_id;
    private Date board_createdAt;
    private Date board_updatedAt;
    private int board_viewCnt;
    private byte board_isDeleted;

    public Board(String user_id, String board_title) {
        this.user_id = user_id;
        this.board_title = board_title;
    }
}
