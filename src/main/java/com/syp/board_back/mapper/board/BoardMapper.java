package com.syp.board_back.mapper.board;

import com.syp.board_back.domain.board.Board;
import com.syp.board_back.domain.board.BoardContent;
import org.apache.ibatis.annotations.*;

@Mapper
public interface BoardMapper {

    @Insert("INSERT INTO tbl_board(board_title, user_id, board_createdAt) " +
            "VALUES (#{board_title}, #{user_id}, now())")
    @Options(useGeneratedKeys = true, keyColumn = "board_id", keyProperty = "board_id")
    Long createBoard(Board board);

    @Insert("INSERT INTO tbl_content(board_id, board_content) VALUES(#{board_id}, #{board_content})")
    @Options(useGeneratedKeys = true, keyColumn = "content_id", keyProperty = "content_id")
    void writeContent(BoardContent content);

    @Select("SELECT * FROM tbl_board WHERE board_id = #{board_id}")
    Board selectBoard(String board_id);

    @Update("UPDATE tbl_board SET board_title = #{board_title}, board_createdAt = now() WHERE board_id = #{board_id}")
    void updateBoard(String board_id, String board_title);

    @Update("UPDATE tbl_content SET board_content = #{board_content} WHERE board_id = #{board_id}")
    void updateContent(String board_id, String board_content);
}
