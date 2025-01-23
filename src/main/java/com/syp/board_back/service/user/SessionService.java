package com.syp.board_back.service.user;

import com.syp.board_back.common.constant.SessionConst;
import com.syp.board_back.domain.user.User;
import com.syp.board_back.dto.user.response.login.LoginResponse;
import com.syp.board_back.dto.user.response.session.SessionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
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
}
