package com.bho.catchtrippingbackend.users.model.dao;

import com.bho.catchtrippingbackend.users.model.UsersDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UsersDao {
    @Select("SELECT * FROM users WHERE user_name = #{userName}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userPassword", column = "user_password"),
            @Result(property = "userEmail", column = "user_email")
    })
    UsersDto findUserByUsername(String userName);

    @Insert("INSERT INTO users (user_name, user_password, user_email) VALUES (#{userName}, #{userPassword}, #{userEmail})")
    void createUser(UsersDto user);

    @Select("SELECT * FROM users WHERE user_email = #{userEmail}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userPassword", column = "user_password"),
            @Result(property = "userEmail", column = "user_email")
    })
    UsersDto findUserByEmail(String email);
}
