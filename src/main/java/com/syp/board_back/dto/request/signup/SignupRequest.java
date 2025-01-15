package com.syp.board_back.dto.request.signup;

import lombok.Data;

@Data
public class SignupRequest {
    private String user_id;
    private String user_password;
    private String user_email;
    private String user_phone;
}
