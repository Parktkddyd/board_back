package com.syp.board_back.service;

import com.syp.board_back.domain.User;
import com.syp.board_back.dto.request.signup.SignupRequest;
import com.syp.board_back.dto.response.common.ResponseCode;
import com.syp.board_back.dto.response.SignUpResponse;
import com.syp.board_back.exception.SignUpException;
import com.syp.board_back.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public SignUpResponse addUser(SignupRequest signupReq){
        User user = new User(signupReq.getUser_id(), signupReq.getUser_password(),
                signupReq.getUser_email(), signupReq.getUser_phone());

        try{
            Long addUserOrder = userMapper.addUser(user);
            return new SignUpResponse(addUserOrder);
        }catch(Exception e){
            throw new SignUpException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

}
