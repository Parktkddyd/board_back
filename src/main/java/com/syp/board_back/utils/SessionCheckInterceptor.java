package com.syp.board_back.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syp.board_back.dto.response.common.ApiResponse;
import com.syp.board_back.dto.response.common.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginMember") == null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ApiResponse<Void> customResponse = ApiResponse.fail(ResponseCode.USER_NOT_ACCESS, null, ResponseCode.USER_NOT_ACCESS.getMessage());

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(customResponse);

            response.getWriter().write(jsonResponse);
            return false;
        }
        return true;
    }
}
