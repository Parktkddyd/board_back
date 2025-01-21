package com.syp.board_back.service.user;

import com.syp.board_back.dto.user.response.login.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    public LoginResponse sessionCheck(HttpServletRequest servletReq) {
        HttpSession session = servletReq.getSession(false);

        return new LoginResponse(session.getId());
    }
}
