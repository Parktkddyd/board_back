package com.syp.board_back.controller;

import com.syp.board_back.dto.request.login.LoginRequest;
import com.syp.board_back.dto.request.signup.DupIdCheckRequest;
import com.syp.board_back.dto.request.signup.SignupRequest;
import com.syp.board_back.dto.response.common.ApiResponse;
import com.syp.board_back.dto.response.common.ResponseCode;
import com.syp.board_back.dto.response.login.LoginResponse;
import com.syp.board_back.dto.response.signup.DupIdCheckResponse;
import com.syp.board_back.dto.response.signup.SignUpResponse;
import com.syp.board_back.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
