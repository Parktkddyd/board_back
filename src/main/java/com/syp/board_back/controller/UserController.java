package com.syp.board_back.controller;

import com.syp.board_back.dto.common.response.ApiResponse;
import com.syp.board_back.dto.common.response.ResponseCode;
import com.syp.board_back.dto.user.request.login.LoginRequest;
import com.syp.board_back.dto.user.request.signup.DupIdCheckRequest;
import com.syp.board_back.dto.user.request.signup.SignupRequest;
import com.syp.board_back.dto.user.response.login.LoginResponse;
import com.syp.board_back.dto.user.response.signup.DupIdCheckResponse;
import com.syp.board_back.dto.user.response.signup.SignUpResponse;
import com.syp.board_back.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup/duplicate")
    public ApiResponse<DupIdCheckResponse> checkDupId(@RequestBody @Valid DupIdCheckRequest req) {
        return ApiResponse.success(ResponseCode.ID_DUP_CHECK_SUCCESS, userService.checkId(req),
                ResponseCode.ID_DUP_CHECK_SUCCESS.getMessage());
    }

    @PostMapping("/signup")
    public ApiResponse<SignUpResponse> signUp(@RequestBody @Valid SignupRequest req) {
        return ApiResponse.success(ResponseCode.USER_CREATE_SUCCESS, userService.addUser(req),
                ResponseCode.USER_CREATE_SUCCESS.getMessage());
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest loginReq, HttpServletRequest servletReq) {
        return ApiResponse.success(ResponseCode.USER_LOGIN_SUCCESS, userService.login(loginReq, servletReq),
                ResponseCode.USER_LOGIN_SUCCESS.getMessage());
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest servletReq) {
        userService.logout(servletReq);
        return ApiResponse.success(ResponseCode.USER_LOGOUT_SUCCESS, null,
                ResponseCode.USER_LOGOUT_SUCCESS.getMessage());
    }

    @GetMapping("/session-check")
    public ApiResponse<LoginResponse> sessionCheck(HttpServletRequest servletReq) {
        return ApiResponse.success(ResponseCode.USER_ACCESS_OK, userService.sessionCheck(servletReq),
                ResponseCode.USER_ACCESS_OK.getMessage());
    }
}
