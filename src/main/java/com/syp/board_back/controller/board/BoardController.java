package com.syp.board_back.controller.board;

import com.syp.board_back.dto.board.request.BoardPostRequest;
import com.syp.board_back.dto.board.request.BoardUpdateRequest;
import com.syp.board_back.dto.board.response.BoardDeleteResponse;
import com.syp.board_back.dto.board.response.BoardPostResponse;
import com.syp.board_back.dto.board.response.BoardReadResponse;
import com.syp.board_back.dto.board.response.BoardUpdateResponse;
import com.syp.board_back.dto.common.response.ApiResponse;
import com.syp.board_back.dto.common.response.ResponseCode;
import com.syp.board_back.service.board.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
                                                   @RequestBody @Valid BoardUpdateRequest updateReq,
                                                   HttpServletRequest servletReq) {
        return ApiResponse.success(ResponseCode.UPDATE_SUCCESS, boardService.update(board_id, updateReq, servletReq),
                ResponseCode.UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping({"/{board_id}", "/"})
    public ApiResponse<BoardDeleteResponse> delete(@PathVariable(required = false) Long board_id,
                                                   HttpServletRequest servletReq) {
        return ApiResponse.success(ResponseCode.DELETE_SUCCESS, boardService.delete(board_id, servletReq),
                ResponseCode.DELETE_SUCCESS.getMessage());
    }

    @GetMapping({"/{board_id}", "/"})
    public ApiResponse<BoardReadResponse> readDetail(@PathVariable(required = false) Long board_id,
                                                     @PageableDefault(size = 10) Pageable page) {

        return ApiResponse.success(ResponseCode.READ_DETAIL_SUCCESS, boardService.readDetail(board_id, page),
                ResponseCode.READ_DETAIL_SUCCESS.getMessage());
    }

    @GetMapping("")
    public ApiResponse<Page<BoardReadResponse>> readList(@PageableDefault(size = 10) Pageable page) {
        Page<BoardReadResponse> result = boardService.readList(page);

        return ApiResponse.success(ResponseCode.READ_LIST_SUCCESS, result,
                ResponseCode.READ_LIST_SUCCESS.getMessage());
    }
}
