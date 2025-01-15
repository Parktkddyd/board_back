package com.syp.board_back.dto.request.signup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank
    private String user_id;
    @NotBlank
    @Size(min = 6, max = 100)
    private String user_password;
    @Email
    private String user_email;
    @NotBlank
    @Size(max = 11)
    private String user_phone;
}
