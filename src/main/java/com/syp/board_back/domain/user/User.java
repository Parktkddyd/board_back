package com.syp.board_back.domain.user;

import lombok.Data;

@Data
public class User {
    private Long user_key;
    private String user_id;
    private String user_password;
    private String user_salt;
    private String user_email;
    private String user_phone;

    public User(String user_id, String user_password, String user_salt,
                String user_email, String user_phone) {
        this.user_id = user_id;
        this.user_password = user_password;
        this.user_salt = user_salt;
        this.user_email = user_email;
        this.user_phone = user_phone;
    }
}
