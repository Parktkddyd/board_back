package com.syp.board_back.service.board;

import com.syp.board_back.domain.board.Comment;
import com.syp.board_back.dto.board.request.CommentPostRequest;
import com.syp.board_back.dto.board.response.CommentPostResponse;
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
}
