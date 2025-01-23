package com.syp.board_back.controller.board;

import com.syp.board_back.common.exception.BoardException;
import com.syp.board_back.dto.board.request.BoardPostRequest;
import com.syp.board_back.dto.board.request.BoardUpdateRequest;
import com.syp.board_back.dto.board.response.BoardDeleteResponse;
import com.syp.board_back.dto.board.response.BoardPostResponse;
import com.syp.board_back.dto.board.response.BoardUpdateResponse;
import com.syp.board_back.dto.common.response.ApiResponse;
import com.syp.board_back.dto.common.response.ResponseCode;
import com.syp.board_back.service.board.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/post")
    public ApiResponse<BoardPostResponse> post(@RequestBody @Valid BoardPostRequest postReq,
                                               HttpServletRequest servletReq) {
        return ApiResponse.success(ResponseCode.POST_SUCCESS, boardService.post(postReq, servletReq),
                ResponseCode.POST_SUCCESS.getMessage());
    }

    @PutMapping({"/{board_id}", "/"})
    public ApiResponse<BoardUpdateResponse> update(@PathVariable(required = false) Long board_id,
                                                   @RequestBody @Valid BoardUpdateRequest updateReq) {
        if (board_id == null) {
            throw new BoardException(ResponseCode.NOT_FOUND);
        }
        return ApiResponse.success(ResponseCode.UPDATE_SUCCESS, boardService.update(board_id, updateReq),
                ResponseCode.UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping({"/{board_id}", "/"})
    public ApiResponse<BoardDeleteResponse> delete(@PathVariable(required = false) Long board_id) {
        if (board_id == null) {
            throw new BoardException(ResponseCode.NOT_FOUND);
        }
        return ApiResponse.success(ResponseCode.DELETE_SUCCESS, boardService.delete(board_id),
                ResponseCode.DELETE_SUCCESS.getMessage());
    }
}
