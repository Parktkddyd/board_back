package com.syp.board_back.dto.board.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardUpdateRequest {
    @NotBlank
    private String board_title;
    @NotBlank
    private String board_content;
}
