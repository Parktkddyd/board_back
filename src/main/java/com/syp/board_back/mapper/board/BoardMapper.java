package com.syp.board_back.mapper.board;

import com.syp.board_back.domain.board.Board;
import com.syp.board_back.domain.board.BoardContent;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface BoardMapper {

    @Insert("Insert INTO tbl_board(board_title, user_id, board_createdAt) " +
            "VALUES (#{board_title}, #{user_id}, now())")
    @Options(useGeneratedKeys = true, keyColumn = "board_id", keyProperty = "board_id")
    Long createBoard(Board board);

    @Insert("INSERT INTO tbl_content(board_id, board_content) VALUES(#{board_id}, #{board_content})")
    @Options(useGeneratedKeys = true, keyColumn = "content_id", keyProperty = "content_id")
    void writeContent(BoardContent content);

}
