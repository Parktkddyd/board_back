package com.syp.board_back.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String user_id;
    private String user_password;
    private String user_salt;
    private String user_email;
    private String user_phone;
}
