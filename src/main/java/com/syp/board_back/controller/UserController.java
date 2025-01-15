package com.syp.board_back.controller;

import com.syp.board_back.dto.request.signup.SignupRequest;
import com.syp.board_back.dto.response.SignUpResponse;
import com.syp.board_back.dto.response.common.ApiResponse;
import com.syp.board_back.dto.response.common.ResponseCode;
import com.syp.board_back.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ApiResponse<SignUpResponse> signUp(@RequestBody SignupRequest req) {
        return ApiResponse.success(userService.addUser(req),
                ResponseCode.USER_CREATE_SUCCESS.getMessage());
    }
}
