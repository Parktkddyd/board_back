package com.syp.board_back.mapper.board;

import com.syp.board_back.domain.board.Board;
import com.syp.board_back.domain.board.BoardContent;
import com.syp.board_back.dto.board.response.BoardReadDetailResponse;
import com.syp.board_back.dto.board.response.BoardReadResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {

    @Insert("INSERT INTO tbl_board(board_title, user_id, board_createdAt) " +
            "VALUES (#{board_title}, #{user_id}, now())")
    @Options(useGeneratedKeys = true, keyColumn = "board_id", keyProperty = "board_id")
    void createBoard(Board board);

    @Insert("INSERT INTO tbl_content(board_id, board_content) VALUES(#{board_id}, #{board_content})")
    @Options(useGeneratedKeys = true, keyColumn = "content_id", keyProperty = "content_id")
    void writeContent(BoardContent content);

    @Select("SELECT b.board_id, b.board_title, b.user_id," +
            "b.board_createdAt, b.board_viewCnt, c.board_content " +
            "FROM tbl_board AS b " +
            "INNER JOIN tbl_content AS c " +
            "ON b.board_id = c.board_id WHERE c.board_id = #{board_id} AND b.board_isDeleted = 0")
    BoardReadDetailResponse selectBoard(Long board_id);

    @Select("SELECT board_id, board_title, user_id, " +
            "board_createdAt, board_viewCnt FROM tbl_board WHERE board_isDeleted = 0 ORDER BY board_id DESC LIMIT #{offset}, #{pageSize}")
    List<BoardReadResponse> selectBoardList(long offset, long pageSize);

    @Select("SELECT COUNT(*) FROM tbl_board WHERE board_isDeleted = 0")
    long countBoardList(long offset, long pageSize);

    @Update("UPDATE tbl_board SET board_title = #{board_title}, board_updatedAt = now() WHERE board_id = #{board_id}")
    long updateBoard(Long board_id, String board_title);

    @Update("UPDATE tbl_content SET board_content = #{board_content} WHERE board_id = #{board_id}")
    long updateContent(Long board_id, String board_content);

    @Update("UPDATE tbl_board SET board_isDeleted = 1 WHERE board_id = #{board_id}")
    long deleteBoard(Long board_id);

    @Select("SELECT user_id FROM tbl_board WHERE board_id = #{board_id}")
    String selectUserbyBoardId(Long board_id);
}
