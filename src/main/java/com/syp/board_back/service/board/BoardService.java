package com.syp.board_back.service.board;

import com.syp.board_back.common.exception.BoardException;
import com.syp.board_back.common.exception.DatabaseException;
import com.syp.board_back.domain.board.Board;
import com.syp.board_back.domain.board.BoardContent;
import com.syp.board_back.dto.board.request.BoardPostRequest;
import com.syp.board_back.dto.board.request.BoardUpdateRequest;
import com.syp.board_back.dto.board.response.BoardDeleteResponse;
import com.syp.board_back.dto.board.response.BoardPostResponse;
import com.syp.board_back.dto.board.response.BoardReadResponse;
import com.syp.board_back.dto.board.response.BoardUpdateResponse;
import com.syp.board_back.dto.common.response.ResponseCode;
import com.syp.board_back.dto.user.response.session.SessionResponse;
import com.syp.board_back.mapper.board.BoardMapper;
import com.syp.board_back.service.user.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private Long createBoard(String user_id, String title) {
        Board board = new Board(user_id, title);
        boardMapper.createBoard(board);
        return board.getBoard_id();
    }

    public BoardPostResponse post(BoardPostRequest postReq, HttpServletRequest servletReq) {
        SessionResponse session = sessionService.getUserIdBySession(servletReq);
        try {
            Long board_id = createBoard(session.getUser_id(), postReq.getBoard_title());
            boardMapper.writeContent(new BoardContent(board_id, postReq.getBoard_content()));
            return new BoardPostResponse(board_id);
        } catch (Exception e) {
            throw new DatabaseException(ResponseCode.DB_SERVER_ERROR);
        }
    }

    public void modifyBoard(Long board_id, String title) {
        boardMapper.updateBoard(board_id, title);
    }

    public BoardUpdateResponse update(Long board_id, BoardUpdateRequest updateReq) {
        try {
            modifyBoard(board_id, updateReq.getBoard_title());
            boardMapper.updateContent(board_id, updateReq.getBoard_content());
            return new BoardUpdateResponse(board_id, updateReq.getBoard_title(),
                    updateReq.getBoard_content());
        } catch (Exception e) {
            throw new DatabaseException(ResponseCode.DB_SERVER_ERROR);
        }
    }

    public BoardDeleteResponse delete(Long board_id) {
        try {
            boardMapper.deleteBoard(board_id);
            return new BoardDeleteResponse(board_id, (byte) 1);
        } catch (Exception e) {
            throw new DatabaseException(ResponseCode.DB_SERVER_ERROR);
        }
    }

    /*READ*/
    public BoardReadResponse readDetail(Long board_id) {
        if (board_id == null) {
            throw new BoardException(ResponseCode.NOT_FOUND);
        }

        BoardReadResponse br = selectById(board_id);

        return Optional.ofNullable(br).
                orElseThrow(() -> new BoardException(ResponseCode.NOT_FOUND));
    }

    public BoardReadResponse selectById(Long board_id) {
        try {
            return boardMapper.selectBoard(board_id);
        } catch (DataAccessException de) {
            throw new DatabaseException(ResponseCode.DB_SERVER_ERROR);
        }
    }
}
