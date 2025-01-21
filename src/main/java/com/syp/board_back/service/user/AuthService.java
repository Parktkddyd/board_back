package com.syp.board_back.service.user;

import com.syp.board_back.common.constant.SessionConst;
import com.syp.board_back.common.exception.DataAccessException;
import com.syp.board_back.common.exception.LoginException;
import com.syp.board_back.common.util.PasswordEncryptUtil;
import com.syp.board_back.domain.User;
import com.syp.board_back.dto.common.response.ResponseCode;
import com.syp.board_back.dto.user.request.login.LoginRequest;
import com.syp.board_back.dto.user.response.login.LoginResponse;
import com.syp.board_back.mapper.user.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AuthService {

    private final UserMapper userMapper;

    public AuthService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public LoginResponse login(LoginRequest loginReq, HttpServletRequest servletReq) {
        String reqId = loginReq.getUser_id();
        String reqPass = loginReq.getUser_password();

        User user = getUserById(reqId);
        validateUser(user, reqId, reqPass);

        //로그인 성공 시 세션을 생성하고 사용자 정보 저장
        HttpSession session = servletReq.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, user);
        return new LoginResponse(session.getId());

    }

    public void logout(HttpServletRequest servletReq) {
        HttpSession session = servletReq.getSession(false);
        session.invalidate();
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
        if (!passwordDecrypt(reqId, reqPass).equals(user.getUser_password())) {
            throw new LoginException(ResponseCode.USER_LOGIN_PASS_FAIL);
        }
    }

    private String passwordDecrypt(String reqId, String reqPass) {
        String salt = userMapper.findSaltById(reqId);

        if (salt == null) {
            throw new DataAccessException(ResponseCode.DB_SERVER_ERROR);
        }

        return PasswordEncryptUtil.sha256EncryptWithSalt(reqPass, salt);

    }
}
