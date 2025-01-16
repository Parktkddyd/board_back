package com.syp.board_back.dto.request.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank
    private String user_id;
    @NotBlank
    private String user_password;
}