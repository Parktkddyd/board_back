package com.syp.board_back.controller.user;

import com.syp.board_back.dto.common.response.ApiResponse;
import com.syp.board_back.dto.common.response.ResponseCode;
import com.syp.board_back.dto.user.request.signup.DupIdCheckRequest;
import com.syp.board_back.dto.user.request.signup.SignupRequest;
import com.syp.board_back.dto.user.response.signup.DupIdCheckResponse;
import com.syp.board_back.dto.user.response.signup.SignUpResponse;
import com.syp.board_back.service.user.SignupService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/signup")
public class SignupController {
    private final SignupService signupService;

    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping("")
    public ApiResponse<SignUpResponse> signUp(@RequestBody @Valid SignupRequest req) {
        return ApiResponse.success(ResponseCode.USER_CREATE_SUCCESS, signupService.addUser(req),
                ResponseCode.USER_CREATE_SUCCESS.getMessage());
    }

    @PostMapping("/duplicate")
    public ApiResponse<DupIdCheckResponse> checkDupId(@RequestBody @Valid DupIdCheckRequest req) {
        return ApiResponse.success(ResponseCode.ID_DUP_CHECK_SUCCESS, signupService.checkId(req),
                ResponseCode.ID_DUP_CHECK_SUCCESS.getMessage());
    }
}
