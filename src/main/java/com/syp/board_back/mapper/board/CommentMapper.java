package com.syp.board_back.mapper.board;

import com.syp.board_back.domain.board.Comment;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CommentMapper {
    @Insert("INSERT INTO tbl_comment(board_id, comment_group, user_id, comment_content, comment_createdAt) " +
            "VALUES (#{board_id}, IFNULL((SELECT MAX(comment_group) FROM tbl_comment as c) + 1, 1), " +
            "#{user_id}, #{comment_content}, now())")
    @Options(useGeneratedKeys = true, keyColumn = "comment_id", keyProperty = "comment_id")
    void postReply(Comment comment);

    @Insert("INSERT INTO tbl_comment(" +
            "board_id, comment_group, comment_level, " +
            "comment_groupOrder, comment_parentId, " +
            "user_id, comment_content, comment_createdAt) " +
            "VALUES (#{board_id}, #{comment_group}, #{comment_level}, " +
            "#{comment_groupOrder}, #{comment_parentId}, " +
            "#{user_id}, #{comment_content}, now())")
    @Options(useGeneratedKeys = true, keyColumn = "comment_id", keyProperty = "comment_id")
    void postReReply(Comment comment);

    @Select("SELECT * FROM tbl_comment as parent where parent.comment_id = #{comment_id}")
    Comment selectParentComment(Long comment_id);

    @Update("UPDATE tbl_comment SET comment_childCount = comment_childCount +1 WHERE comment_id = #{comment_id]}")
    Long increaseChildCount(Long comment_id);

    @Update("UPDATE tbl_comment SET comment_groupOrder = comment_groupOrder +1 " +
            "WHERE comment_group = #{comment_group} AND " +
            "comment_groupOrder > (#{comment_childCount} + #{comment_groupOrder})")
    void increaseGroupOrder(int comment_group, int comment_childCount, int comment_groupOrder);

    
}
