package com.bho.catchtrippingbackend.user.dao;

import com.bho.catchtrippingbackend.user.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {
    @Select("SELECT * FROM user WHERE user_name = #{userName}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userPassword", column = "user_password"),
            @Result(property = "userEmail", column = "user_email")
    })
    User findUserByUsername(String userName);

    @Select("SELECT count(*) FROM user WHERE user_name = #{userName}")
    int countUsersByUserName(String userName);

    @Insert("INSERT INTO user (user_name, user_password, user_email) VALUES (#{userName}, #{userPassword}, #{userEmail})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void createUser(User user);

    @Select("SELECT count(*) FROM user WHERE user_email = #{userEmail}")
    int countUsersByEmail(String email);
}
