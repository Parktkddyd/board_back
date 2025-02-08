package com.syp.board_back.common.interceptor;

import com.syp.board_back.common.constant.SessionConst;
import com.syp.board_back.common.exception.SessionException;
import com.syp.board_back.dto.common.response.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (StringUtils.pathEquals(request.getMethod(), "OPTIONS")) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
            throw new SessionException(ResponseCode.USER_NOT_ACCESS);
        }
        return true;
    }
}
