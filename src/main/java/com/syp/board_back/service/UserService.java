package com.syp.board_back.service;

import com.syp.board_back.domain.User;
import com.syp.board_back.dto.request.login.LoginRequest;
import com.syp.board_back.dto.request.signup.DupIdCheckRequest;
import com.syp.board_back.dto.request.signup.SignupRequest;
import com.syp.board_back.dto.response.common.ResponseCode;
import com.syp.board_back.dto.response.login.LoginResponse;
import com.syp.board_back.dto.response.signup.DupIdCheckResponse;
import com.syp.board_back.dto.response.signup.SignUpResponse;
import com.syp.board_back.exception.DataAccessException;
import com.syp.board_back.exception.LoginException;
import com.syp.board_back.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
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

    @Transactional
    public SignUpResponse addUser(SignupRequest signupReq) {
        User user = new User(signupReq.getUser_id(), signupReq.getUser_password(),
                signupReq.getUser_email(), signupReq.getUser_phone());

        try {
            Long addUserOrder = userMapper.addUser(user);
            return new SignUpResponse(addUserOrder);
        } catch (DuplicateKeyException dke) {
            throw new DataAccessException(ResponseCode.DB_DUPLICATE_ERROR);
        } catch (Exception e) {
            throw new DataAccessException(ResponseCode.DB_SERVER_ERROR);
        }
    }

    public LoginResponse login(LoginRequest loginReq, HttpServletRequest servletReq) {
        String reqId = loginReq.getUser_id();
        String reqPass = loginReq.getUser_password();

        User user = getUserById(reqId);
        validateUser(user, reqId, reqPass);

        //로그인 성공 시 세션을 생성하고 사용자 정보 저장
        HttpSession session = servletReq.getSession();
        return new LoginResponse(session.getId());

    }

    private User getUserById(String userId) {
        // 사용자 정보 조회
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new LoginException(ResponseCode.USER_LOGIN_ID_FAIL); // 사용자 ID가 없으면 로그인 실패
        }
        return user;
    }

    private void validateUser(User user, String reqId, String reqPass) {
        // 사용자 ID와 비밀번호를 검증
        if (!reqId.equals(user.getUser_id())) {
            throw new LoginException(ResponseCode.USER_LOGIN_ID_FAIL);
        }
        if (!reqPass.equals(user.getUser_password())) {
            throw new LoginException(ResponseCode.USER_LOGIN_PASS_FAIL);
        }
    }


}
