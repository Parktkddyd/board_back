package com.syp.board_back.controller.board;

import com.syp.board_back.dto.board.request.CommentPostRequest;
import com.syp.board_back.dto.board.response.CommentPostResponse;
import com.syp.board_back.dto.common.response.ApiResponse;
import com.syp.board_back.dto.common.response.ResponseCode;
import com.syp.board_back.service.board.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards/{board_id}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post")
    ApiResponse<CommentPostResponse> postComment(@PathVariable("board_id") Long board_id,
                                                 @RequestBody CommentPostRequest postReq,
                                                 HttpServletRequest servletReq) {
        return ApiResponse.success(ResponseCode.POST_SUCCESS, commentService.postReply(board_id, postReq, servletReq),
                ResponseCode.POST_SUCCESS.getMessage());
    }

}
