package com.syp.board_back.service.user;

import com.syp.board_back.common.exception.DataAccessException;
import com.syp.board_back.common.util.PasswordEncryptUtil;
import com.syp.board_back.domain.user.User;
import com.syp.board_back.dto.common.response.ResponseCode;
import com.syp.board_back.dto.user.request.signup.DupIdCheckRequest;
import com.syp.board_back.dto.user.request.signup.SignupRequest;
import com.syp.board_back.dto.user.response.signup.DupIdCheckResponse;
import com.syp.board_back.dto.user.response.signup.SignUpResponse;
import com.syp.board_back.mapper.user.UserMapper;
import org.springframework.dao.DuplicateKeyException;
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
        try {
            boolean isDuplicate = userMapper.checkDupId(reqId);
            return new DupIdCheckResponse(reqId, isDuplicate);
        } catch (Exception e) {
            throw new DataAccessException(ResponseCode.DB_SERVER_ERROR);
        }
    }

    @Transactional
    public SignUpResponse addUser(SignupRequest signupReq) {
        String salt = PasswordEncryptUtil.getSalt();

        User user = new User(signupReq.getUser_id(),
                PasswordEncryptUtil.sha256EncryptWithSalt(signupReq.getUser_password(), salt),
                salt,
                signupReq.getUser_email(),
                signupReq.getUser_phone());

        try {
            Long addUserOrder = userMapper.addUser(user);
            return new SignUpResponse(addUserOrder);
        } catch (DuplicateKeyException dke) {
            throw new DataAccessException(ResponseCode.DB_DUPLICATE_ERROR);
        } catch (Exception e) {
            throw new DataAccessException(ResponseCode.DB_SERVER_ERROR);
        }
    }
}
