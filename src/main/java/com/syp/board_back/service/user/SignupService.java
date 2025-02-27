package com.syp.board_back.service.user;

import com.syp.board_back.common.util.PasswordEncryptUtil;
import com.syp.board_back.domain.user.User;
import com.syp.board_back.dto.user.request.signup.DupIdCheckRequest;
import com.syp.board_back.dto.user.request.signup.SignupRequest;
import com.syp.board_back.dto.user.response.signup.DupIdCheckResponse;
import com.syp.board_back.dto.user.response.signup.SignUpResponse;
import com.syp.board_back.mapper.user.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupService {
    private final UserMapper userMapper;

    public SignupService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public DupIdCheckResponse checkId(DupIdCheckRequest checkIdReq) {
        String reqId = checkIdReq.getUser_id();
        boolean isDuplicate = userMapper.checkDupId(reqId);

        return new DupIdCheckResponse(reqId, isDuplicate);
    }

    @Transactional
    public SignUpResponse addUser(SignupRequest signupReq) {
        String salt = PasswordEncryptUtil.getSalt();

        User user = new User(signupReq.getUser_id(),
                PasswordEncryptUtil.sha256EncryptWithSalt(signupReq.getUser_password(), salt),
                salt,
                signupReq.getUser_email(),
                signupReq.getUser_phone());

        userMapper.addUser(user);
        Long addUserOrder = user.getUser_key();
        return new SignUpResponse(addUserOrder);

    }
}
