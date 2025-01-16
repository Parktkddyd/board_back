package com.syp.board_back.service;

import com.syp.board_back.domain.User;
import com.syp.board_back.dto.request.signup.DupIdCheckRequest;
import com.syp.board_back.dto.request.signup.SignupRequest;
import com.syp.board_back.dto.response.common.ResponseCode;
import com.syp.board_back.dto.response.signup.DupIdCheckResponse;
import com.syp.board_back.dto.response.signup.SignUpResponse;
import com.syp.board_back.exception.DataAccessException;
import com.syp.board_back.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public DupIdCheckResponse checkId(DupIdCheckRequest checkIdReq) {
        String reqId = checkIdReq.getUser_id();
        try {
            boolean isDuplicate = userMapper.checkDupId(reqId);
            return new DupIdCheckResponse(reqId, isDuplicate);
        } catch (Exception e) {
            throw new DataAccessException(ResponseCode.DB_SERVER_ERROR);
        }
    }

    public SignUpResponse addUser(SignupRequest signupReq) {
        User user = new User(signupReq.getUser_id(), signupReq.getUser_password(),
                signupReq.getUser_email(), signupReq.getUser_phone());

        try {
            Long addUserOrder = userMapper.addUser(user);
            return new SignUpResponse(addUserOrder);
        } catch (Exception e) {
            throw new DataAccessException(ResponseCode.DB_SERVER_ERROR);
        }
    }

}
