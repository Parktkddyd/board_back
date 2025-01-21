package com.syp.board_back.controller.user;

import com.syp.board_back.dto.common.response.ApiResponse;
import com.syp.board_back.dto.common.response.ResponseCode;
import com.syp.board_back.dto.user.request.login.LoginRequest;
import com.syp.board_back.dto.user.response.login.LoginResponse;
import com.syp.board_back.service.user.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest loginReq, HttpServletRequest servletReq) {
        return ApiResponse.success(ResponseCode.USER_LOGIN_SUCCESS, authService.login(loginReq, servletReq),
                ResponseCode.USER_LOGIN_SUCCESS.getMessage());
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest servletReq) {
        authService.logout(servletReq);
        return ApiResponse.success(ResponseCode.USER_LOGOUT_SUCCESS, null,
                ResponseCode.USER_LOGOUT_SUCCESS.getMessage());
    }
}
