package com.syp.board_back.service.board;

import com.syp.board_back.common.exception.BoardException;
import com.syp.board_back.domain.board.Comment;
import com.syp.board_back.dto.board.request.CommentPostRequest;
import com.syp.board_back.dto.board.request.CommentUpdateRequest;
import com.syp.board_back.dto.board.request.ReCommentPostRequest;
import com.syp.board_back.dto.board.response.*;
import com.syp.board_back.dto.common.response.ResponseCode;
import com.syp.board_back.mapper.board.CommentMapper;
import com.syp.board_back.service.user.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentService {
    private final CommentMapper commentMapper;
    private final SessionService sessionService;

    public CommentService(CommentMapper commentMapper, SessionService sessionService) {
        this.commentMapper = commentMapper;
        this.sessionService = sessionService;
    }

    public CommentPostResponse postReply(Long board_id, CommentPostRequest postReq, HttpServletRequest servletReq) {
        if (board_id == null) {
            throw new BoardException(ResponseCode.NOT_FOUND);
        }

        String user_id = sessionService.getUserIdBySession(servletReq).getUser_id();
        Comment comment = new Comment(board_id, user_id, postReq.getComment_content());
        commentMapper.postReply(comment);
        return new CommentPostResponse(board_id, comment.getComment_id(), user_id);
    }

    public ReCommentPostResponse postReReply(ReCommentPostRequest postReq, HttpServletRequest servletReq) {
        String user_id = sessionService.getUserIdBySession(servletReq).getUser_id();
        Long parent_id = postReq.getParent_id();
        if (parent_id == null) {
            throw new BoardException(ResponseCode.NOT_FOUND);
        }
        //1. 부모 댓글을 불러옴
        Comment parentComment = commentMapper.selectParentComment(parent_id);
        //2. 같은 부모를 가진 댓글 중 순서가 최대인 댓글 정보 가져옴
        Comment maxGroupOrderComment = commentMapper.maxGroupOrder(parent_id);
        int maxGroupOrder = parentComment.getComment_groupOrder();
        int maxChildCount = 0;

        if (maxGroupOrderComment != null) {
            maxGroupOrder = maxGroupOrderComment.getComment_groupOrder();
            maxChildCount = maxGroupOrderComment.getComment_childCount();
        }

        //3. 삽입할 댓글의 순서보다 큰 댓글들의 순서를 1씩 증가
        increaseGroupOrder(parentComment.getComment_group(),
                maxGroupOrder + maxChildCount + 1);

        //3. VO에 매핑 후 comment 삽입
        Comment reComment = new Comment(user_id, parent_id, postReq.getComment_content(),
                parentComment.getComment_group(), parentComment.getComment_level() + 1,
                maxGroupOrder + maxChildCount + 1,
                parentComment.getBoard_id());
        commentMapper.postReReply(reComment);


        //5. 부모의 자식 개수 1 증가 (댓글 추가로 인한)
        increaseChildCount(parent_id);

        return new ReCommentPostResponse(reComment.getBoard_id(), reComment.getComment_id(),
                user_id, parent_id);
    }

    private void increaseChildCount(Long comment_id) {
        commentMapper.increaseChildCount(comment_id);
    }

    private void increaseGroupOrder(int group, int groupOrder) {
        commentMapper.increaseGroupOrder(group, groupOrder);
    }

    public CommentUpdateResponse updateReply(Long comment_id, CommentUpdateRequest updateReq,
                                             HttpServletRequest servletReq) {
        findCommentId(comment_id);
        sessionService.CommentPermissionCheck(servletReq, comment_id);

        long updateResult = commentMapper.updateReply(comment_id, updateReq.getComment_content());

        if (updateResult <= 0) {
            throw new BoardException(ResponseCode.NOT_FOUND);
        }

        return new CommentUpdateResponse(comment_id, updateReq.getComment_content());
    }

    public CommentDeleteResponse deleteReply(Long comment_id, HttpServletRequest servletReq) {
        findCommentId(comment_id);
        sessionService.CommentPermissionCheck(servletReq, comment_id);

        long deleteResult = commentMapper.deleteReply(comment_id);

        if (deleteResult <= 0) {
            throw new BoardException(ResponseCode.NOT_FOUND);
        }

        commentMapper.decreaseParentChildCount(comment_id);

        return new CommentDeleteResponse(comment_id, (byte) 1);
    }

    public Page<CommentReadResponse> readReplyList(Long board_id, Pageable page) {
        long offset = page.getOffset();
        long pageSize = page.getPageSize();

        List<Comment> comments = commentMapper.selectReplyList(board_id, offset, pageSize);
        long counts = commentMapper.countComment(board_id, offset, pageSize);
        List<CommentReadResponse> responseList = new ArrayList<>();

        for (Comment comment : comments) {
            CommentReadResponse readResponse =
                    new CommentReadResponse(comment.getComment_id(), comment.getComment_level(),
                            comment.getComment_parentId(), comment.getUser_id(),
                            comment.getComment_content(), comment.getComment_createdAt(), comment.getComment_isDeleted());
            responseList.add(readResponse);
        }

        return new PageImpl<>(responseList, page, counts);
    }

    private void findCommentId(Long comment_id) {
        if (comment_id == null) {
            throw new BoardException(ResponseCode.NOT_FOUND);
        }
    }
}
