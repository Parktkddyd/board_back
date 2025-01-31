package com.syp.board_back.service.board;

import com.syp.board_back.common.exception.BoardException;
import com.syp.board_back.domain.board.Comment;
import com.syp.board_back.dto.board.request.CommentPostRequest;
import com.syp.board_back.dto.board.request.CommentUpdateRequest;
import com.syp.board_back.dto.board.request.ReCommentPostRequest;
import com.syp.board_back.dto.board.response.CommentPostResponse;
import com.syp.board_back.dto.board.response.CommentUpdateResponse;
import com.syp.board_back.dto.board.response.ReCommentPostResponse;
import com.syp.board_back.dto.common.response.ResponseCode;
import com.syp.board_back.mapper.board.CommentMapper;
import com.syp.board_back.service.user.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String user_id = sessionService.getUserIdBySession(servletReq).getUser_id();
        Comment comment = new Comment(board_id, user_id, postReq.getComment_content());
        commentMapper.postReply(comment);
        return new CommentPostResponse(board_id, comment.getComment_id(), user_id);
    }

    public ReCommentPostResponse postReReply(ReCommentPostRequest postReq, HttpServletRequest servletReq) {
        String user_id = sessionService.getUserIdBySession(servletReq).getUser_id();
        Long parent_id = postReq.getParent_id();
        //1. 부모 댓글 정보를 가져옴
        Comment parentComment = commentMapper.selectParentComment(parent_id);

        //2. 부모와 같은 group에서 부모의 자식 댓글 개수 + 부모의 그룹 내의 순서의 값을 합한 값보다 큰 순서들을 1씩 더함
        increaseGroupOrder(parentComment.getComment_group(),
                parentComment.getComment_childCount(),
                parentComment.getComment_groupOrder());

        //3. 부모의 자식 개수 1 증가 (댓글 추가로 인한)
        long updateChildCount = increaseChildCount(parent_id);
        if (updateChildCount <= 0) {
            throw new BoardException(ResponseCode.NOT_FOUND);
        }

        //3. 결과를 VO에 매핑
        Comment reComment = new Comment(user_id, parent_id, postReq.getComment_content(),
                parentComment.getComment_group(), parentComment.getComment_level() + 1,
                parentComment.getComment_groupOrder() + parentComment.getComment_childCount() + 1,
                parentComment.getBoard_id());

        commentMapper.postReReply(reComment);

        return new ReCommentPostResponse(reComment.getBoard_id(), reComment.getComment_id(),
                user_id, parent_id);
    }

    private long increaseChildCount(Long comment_id) {
        return commentMapper.increaseChildCount(comment_id);
    }

    private void increaseGroupOrder(int group, int childCount, int groupOrder) {
        commentMapper.increaseGroupOrder(group, childCount, groupOrder);
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

    private void findCommentId(Long comment_id) {
        if (comment_id == null) {
            throw new BoardException(ResponseCode.NOT_FOUND);
        }
    }
}
