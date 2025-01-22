package com.syp.board_back.mapper.board;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface BoardMapper {

    @Insert("Insert INTO tbl_board(board_title, user_id, board_createdAt) " +
            "VALUES (#{board_title}, #{user_id}, now())")
    @Options(useGeneratedKeys = true, keyColumn = "board_id")
    public Long createBoard(String user_id, String board_title);

    @Insert("INSERT INTO tbl_content(board_id, board_content) VALUES(#{board_id}, #{board_content})")
    @Options(useGeneratedKeys = true, keyColumn = "content_id")
    public void writeContent(Long board_id, String board_content);

}
