package com.syp.board_back.controller.board;

import com.syp.board_back.dto.board.request.CommentPostRequest;
import com.syp.board_back.dto.board.request.CommentUpdateRequest;
import com.syp.board_back.dto.board.request.ReCommentPostRequest;
import com.syp.board_back.dto.board.response.*;
import com.syp.board_back.dto.common.response.ApiResponse;
import com.syp.board_back.dto.common.response.ResponseCode;
import com.syp.board_back.service.board.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards/{board_id}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post")
    ApiResponse<CommentPostResponse> postComment(@PathVariable(value = "board_id", required = false) Long board_id,
                                                 @RequestBody CommentPostRequest postReq,
                                                 HttpServletRequest servletReq) {
        return ApiResponse.success(ResponseCode.POST_SUCCESS, commentService.postReply(board_id, postReq, servletReq),
                ResponseCode.POST_SUCCESS.getMessage());
    }

    @PostMapping("/repost")
    ApiResponse<ReCommentPostResponse> postReComment(@RequestBody ReCommentPostRequest repostReq,
                                                     HttpServletRequest servletReq) {
        return ApiResponse.success(ResponseCode.POST_SUCCESS, commentService.postReReply(repostReq, servletReq),
                ResponseCode.POST_SUCCESS.getMessage());
    }

    @PutMapping("/{comment_id}")
    ApiResponse<CommentUpdateResponse> updateComment(@PathVariable(value = "comment_id", required = false) Long comment_id,
                                                     @RequestBody CommentUpdateRequest updateReq,
                                                     HttpServletRequest servletReq) {
        return ApiResponse.success(ResponseCode.UPDATE_SUCCESS, commentService.updateReply(comment_id, updateReq, servletReq),
                ResponseCode.UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{comment_id}")
    ApiResponse<CommentDeleteResponse> deleteComment(@PathVariable(value = "comment_id", required = false) Long comment_id,
                                                     HttpServletRequest servletReq) {
        return ApiResponse.success(ResponseCode.DELETE_SUCCESS, commentService.deleteReply(comment_id, servletReq),
                ResponseCode.DELETE_SUCCESS.getMessage());
    }

    @GetMapping("")
    ApiResponse<Page<CommentReadResponse>> readCommentList(@PathVariable(value = "board_id", required = false) Long board_id,
                                                           @PageableDefault(size = 10) Pageable pageable) {
        return ApiResponse.success(ResponseCode.READ_LIST_SUCCESS, commentService.readReplyList(board_id, pageable),
                ResponseCode.READ_LIST_SUCCESS.getMessage());
    }
}
