package com.syp.board_back.service.user;

import com.syp.board_back.common.constant.SessionConst;
import com.syp.board_back.common.exception.SessionException;
import com.syp.board_back.domain.user.User;
import com.syp.board_back.dto.common.response.ResponseCode;
import com.syp.board_back.dto.user.response.login.LoginResponse;
import com.syp.board_back.dto.user.response.session.SessionResponse;
import com.syp.board_back.mapper.board.BoardMapper;
import com.syp.board_back.mapper.board.CommentMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final BoardMapper boardMapper;
    private final CommentMapper commentMapper;

    public SessionService(BoardMapper boardMapper, CommentMapper commentMapper) {
        this.boardMapper = boardMapper;
        this.commentMapper = commentMapper;
    }

    public LoginResponse sessionCheck(HttpServletRequest servletReq) {
        HttpSession session = servletReq.getSession(false);
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        return new LoginResponse(user.getUser_id(), true);
    }

    public SessionResponse getUserIdBySession(HttpServletRequest servletReq) {
        HttpSession session = servletReq.getSession(false);
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        String user_id = user.getUser_id();
        return new SessionResponse(user_id);
    }

    public SessionResponse permissionCheck(HttpServletRequest servletReq, Long board_id) {
        SessionResponse session = getUserIdBySession(servletReq);
        String user_id = session.getUser_id();

        String board_user_id = boardMapper.selectUserbyBoardId(board_id);

        if (!user_id.equals(board_user_id)) {
            throw new SessionException(ResponseCode.USER_NOT_ACCESS);
        }

        return session;
    }

    public SessionResponse CommentPermissionCheck(HttpServletRequest servletReq, Long comment_id) {
        SessionResponse session = getUserIdBySession(servletReq);
        String user_id = session.getUser_id();

        String comment_user_id = commentMapper.selectUserbyCommentId(comment_id);

        if (!user_id.equals(comment_user_id)) {
            throw new SessionException(ResponseCode.USER_NOT_ACCESS);
        }

        return session;
    }
}
