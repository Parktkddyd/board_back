package com.syp.board_back.mapper.board;

import com.syp.board_back.domain.board.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    @Select("SELECT * FROM tbl_comment " +
            "WHERE comment_parentId = #{comment_parentId} AND " +
            "comment_groupOrder = (SELECT MAX(comment_groupOrder) FROM tbl_comment as c WHERE comment_parentId = #{comment_parentId})")
    Comment maxGroupOrder(Long comment_parentId);

    @Update("UPDATE tbl_comment SET comment_childCount = comment_childCount +1 WHERE comment_id = #{comment_id]}")
    void increaseChildCount(Long comment_id);

    @Update("UPDATE tbl_comment SET comment_groupOrder = comment_groupOrder + 1 " +
            "WHERE comment_group = #{comment_group} AND " +
            "comment_groupOrder >= (#{comment_groupOrder})")
    void increaseGroupOrder(int comment_group, int comment_groupOrder);

    @Update("UPDATE tbl_comment SET comment_content = #{comment_content}, comment_updatedAt = now() " +
            "WHERE comment_id = #{comment_id}")
    long updateReply(Long comment_id, String comment_content);

    @Update("UPDATE tbl_comment SET comment_content = '삭제된 댓글입니다', comment_isDeleted = 1 " +
            "WHERE comment_id = #{comment_id}")
    long deleteReply(Long comment_id);

    @Update("UPDATE tbl_comment as c " +
            "JOIN(SELECT IFNULL(comment_parentId, -1) as comment_parentId FROM tbl_comment WHERE comment_id = #{comment_id}) as parent " +
            "ON c.comment_id = parent.comment_parentId " +
            "SET c.comment_childCount = c.comment_ChildCount - 1")
    void decreaseParentChildCount(Long comment_id);

    @Select("SELECT * FROM tbl_comment WHERE board_id = #{board_id} AND " +
            "((comment_childCount > 0 AND comment_isDeleted = 1) " +
            "OR comment_isDeleted = 0) " +
            "ORDER BY comment_group asc, comment_groupOrder asc " +
            "LIMIT #{offset}, #{pageSize} ")
    List<Comment> selectReplyList(long board_id, long offset, long pageSize);

    @Select("SELECT COUNT(*) FROM tbl_comment " +
            "WHERE board_id = #{board_id} AND ((comment_childCount > 0 AND comment_isDeleted = 1) " +
            "OR comment_isDeleted = 0)")
    long countComment(Long board_id, long offset, long pageSize);

    @Select("SELECT user_id FROM tbl_comment WHERE comment_id = #{comment_id}")
    String selectUserbyCommentId(Long comment_id);
}
