package com.syp.board_back.service.board;

import com.syp.board_back.common.exception.DataAccessException;
import com.syp.board_back.dto.board.request.BoardPostRequest;
import com.syp.board_back.dto.board.response.BoardPostResponse;
import com.syp.board_back.dto.common.response.ResponseCode;
import com.syp.board_back.mapper.board.BoardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BoardService {
    private final BoardMapper boardMapper;

    public BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    private Long createBoard(String user_id, String title) {
        return boardMapper.createBoard(user_id, title);
    }

    public BoardPostResponse post(BoardPostRequest postReq) {
        try {
            Long board_id = createBoard(postReq.getUser_id(), postReq.getBoard_title());
            boardMapper.writeContent(board_id, postReq.getBoard_content());
            return new BoardPostResponse(board_id);
        } catch (Exception e) {
            throw new DataAccessException(ResponseCode.DB_SERVER_ERROR);
        }
    }
}
