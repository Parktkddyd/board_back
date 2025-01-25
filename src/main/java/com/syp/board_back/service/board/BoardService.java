package com.syp.board_back.service.board;

import com.syp.board_back.common.exception.BoardException;
import com.syp.board_back.domain.board.Board;
import com.syp.board_back.domain.board.BoardContent;
import com.syp.board_back.dto.board.request.BoardPostRequest;
import com.syp.board_back.dto.board.request.BoardUpdateRequest;
import com.syp.board_back.dto.board.response.*;
import com.syp.board_back.dto.common.response.ResponseCode;
import com.syp.board_back.dto.user.response.session.SessionResponse;
import com.syp.board_back.mapper.board.BoardMapper;
import com.syp.board_back.service.user.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class BoardService {
    private final BoardMapper boardMapper;
    private final SessionService sessionService;

    public BoardService(BoardMapper boardMapper, SessionService sessionService) {
        this.boardMapper = boardMapper;
        this.sessionService = sessionService;
    }

    //Create

    public BoardPostResponse post(BoardPostRequest postReq, HttpServletRequest servletReq) {
        SessionResponse session = sessionService.getUserIdBySession(servletReq);
        Long board_id = createBoard(session.getUser_id(), postReq.getBoard_title());
        boardMapper.writeContent(new BoardContent(board_id, postReq.getBoard_content()));
        return new BoardPostResponse(board_id);
    }

    private Long createBoard(String user_id, String title) {
        Board board = new Board(user_id, title);
        boardMapper.createBoard(board);
        return board.getBoard_id();
    }

    //Update
    public BoardUpdateResponse update(Long board_id, BoardUpdateRequest updateReq, HttpServletRequest servletReq) {
        findBoardId(board_id);
        sessionService.permissionCheck(servletReq, board_id);

        Long UpdateBoardResult = modifyBoard(board_id, updateReq.getBoard_title());
        if (UpdateBoardResult <= 0) {
            throw new BoardException(ResponseCode.NOT_FOUND);
        }

        Long UpdateContentResult = modifyContent(board_id, updateReq.getBoard_content());
        if (UpdateContentResult <= 0) {
            throw new BoardException(ResponseCode.NOT_FOUND);
        }

        return new BoardUpdateResponse(board_id, updateReq.getBoard_title(),
                updateReq.getBoard_content());
    }

    public Long modifyBoard(Long board_id, String title) {
        return boardMapper.updateBoard(board_id, title);
    }

    public Long modifyContent(Long board_id, String content) {
        return boardMapper.updateContent(board_id, content);
    }

    //Delete
    public BoardDeleteResponse delete(Long board_id, HttpServletRequest servletReq) {
        findBoardId(board_id);
        sessionService.permissionCheck(servletReq, board_id);

        Long DeleteResult = deleteDb(board_id);

        if (DeleteResult <= 0) {
            throw new BoardException(ResponseCode.NOT_FOUND);
        }

        return new BoardDeleteResponse(board_id, (byte) 1);
    }

    public Long deleteDb(Long board_id) {
        return boardMapper.deleteBoard(board_id);
    }

    //SelectOne
    public BoardReadDetailResponse readDetail(Long board_id) {
        findBoardId(board_id);

        BoardReadDetailResponse br = selectDb(board_id);

        return Optional.ofNullable(br).
                orElseThrow(() -> new BoardException(ResponseCode.NOT_FOUND));
    }

    public BoardReadDetailResponse selectDb(Long board_id) {
        return boardMapper.selectBoard(board_id);
    }

    //SelectList
    public ArrayList<BoardReadResponse> readList() {
        return boardMapper.selectBoardList();
    }

    public void findBoardId(Long board_id) {
        if (board_id == null) {
            throw new BoardException(ResponseCode.NOT_FOUND);
        }
    }
}
