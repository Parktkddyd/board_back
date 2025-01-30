package com.syp.board_back.mapper.board;

import com.syp.board_back.domain.board.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface CommentMapper {
    @Insert("INSERT INTO tbl_comment(board_id, comment_group, user_id, comment_content, comment_createdAt) " +
            "VALUES (#{board_id}, IFNULL((SELECT MAX(comment_group) FROM tbl_comment as c) + 1, 1), " +
            "#{user_id}, #{comment_content}, now())")
    @Options(useGeneratedKeys = true, keyColumn = "comment_id", keyProperty = "comment_id")
    void postReply(Comment comment);
}
