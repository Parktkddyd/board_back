package com.syp.board_back.dto.user.request.signup;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DupIdCheckRequest {
    @NotBlank
    private String user_id;
}
