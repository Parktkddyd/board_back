package com.syp.board_back.dto.user.response.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String user_id;
    private boolean success;
}
