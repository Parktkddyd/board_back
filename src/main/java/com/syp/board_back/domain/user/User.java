package com.syp.board_back.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private final Long user_key;
    private final String user_id;
    private final String user_password;
    private final String user_salt;
    private final String user_email;
    private final String user_phone;


    //회원가입 시 사용할 생성자
    public User(String user_id, String user_password, String user_salt,
                String user_email, String user_phone) {
        this(null, user_id, user_password, user_salt,
                user_email, user_phone);
    }
}
