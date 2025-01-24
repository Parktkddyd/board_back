package com.syp.board_back.mapper.board;

import com.syp.board_back.domain.board.Board;
import com.syp.board_back.domain.board.BoardContent;
import com.syp.board_back.dto.board.response.BoardReadResponse;
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

    @Select("SELECT b.board_id, b.board_title, b.user_id," +
            "b.board_createdAt, b.board_viewCnt, c.board_content " +
            "FROM tbl_board AS b " +
            "INNER JOIN tbl_content AS c " +
            "ON b.board_id = c.board_id WHERE c.board_id = #{board_id} AND b.board_isDeleted = 0")
    BoardReadResponse selectBoard(Long board_id);

    @Update("UPDATE tbl_board SET board_title = #{board_title}, board_createdAt = now() WHERE board_id = #{board_id}")
    Long updateBoard(Long board_id, String board_title);

    @Update("UPDATE tbl_content SET board_content = #{board_content} WHERE board_id = #{board_id}")
    Long updateContent(Long board_id, String board_content);

    @Update("UPDATE tbl_board SET board_isDeleted = 1 WHERE board_id = #{board_id}")
    Long deleteBoard(Long board_id);
}
