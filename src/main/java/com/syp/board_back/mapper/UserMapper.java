package com.syp.board_back.mapper;

import com.syp.board_back.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("SELECT IF(EXISTS (SELECT 1 FROM tbl_user WHERE user_id = #{user_id}), true, false)")
    boolean checkDupId(String id);

    @Update("INSERT INTO tbl_user(user_id, user_password, user_email, user_phone) VALUES (#{user_id}, #{user_password}, #{user_email}, #{user_phone})")
    @Options(useGeneratedKeys = true, keyColumn = "user_key")
    Long addUser(User user);

    @Select("SELECT * FROM tbl_user WHERE user_id = #{user_id}")
    User findById(String id);
}
