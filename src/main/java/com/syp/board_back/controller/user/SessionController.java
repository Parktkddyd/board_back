package com.syp.board_back.controller.user;

import com.syp.board_back.dto.common.response.ApiResponse;
import com.syp.board_back.dto.common.response.ResponseCode;
import com.syp.board_back.dto.user.response.login.LoginResponse;
import com.syp.board_back.service.user.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/session")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/check")
    public ApiResponse<LoginResponse> sessionCheck(HttpServletRequest servletReq) {
        return ApiResponse.success(ResponseCode.USER_ACCESS_OK, sessionService.sessionCheck(servletReq),
                ResponseCode.USER_ACCESS_OK.getMessage());
    }
}
