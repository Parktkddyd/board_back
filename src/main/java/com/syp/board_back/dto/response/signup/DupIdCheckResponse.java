package com.syp.board_back.dto.response.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DupIdCheckResponse {
    private String user_id;
    private boolean isDuplicate;
}
