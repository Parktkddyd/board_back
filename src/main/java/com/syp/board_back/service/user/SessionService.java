package com.syp.board_back.service.user;

import com.syp.board_back.common.constant.SessionConst;
import com.syp.board_back.common.exception.SessionException;
import com.syp.board_back.domain.user.User;
import com.syp.board_back.dto.common.response.ResponseCode;
import com.syp.board_back.dto.user.response.login.LoginResponse;
import com.syp.board_back.dto.user.response.session.SessionResponse;
import com.syp.board_back.mapper.board.BoardMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final BoardMapper boardMapper;

    public SessionService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public LoginResponse sessionCheck(HttpServletRequest servletReq) {
        HttpSession session = servletReq.getSession(false);

        return new LoginResponse(session.getId());
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
}
