package com.syp.board_back.controller.board;

import com.syp.board_back.dto.board.request.BoardPostRequest;
import com.syp.board_back.dto.board.response.BoardPostResponse;
import com.syp.board_back.dto.common.response.ApiResponse;
import com.syp.board_back.dto.common.response.ResponseCode;
import com.syp.board_back.service.board.BoardService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/post")
    public ApiResponse<BoardPostResponse> post(@RequestBody @Valid BoardPostRequest postReq) {
        return ApiResponse.success(ResponseCode.POST_SUCCESS, boardService.post(postReq),
                ResponseCode.POST_SUCCESS.getMessage());
    }
}
