package com.bho.catchtrippingbackend.user.entity;

import lombok.*;

import java.time.LocalDateTime;

//@Data
@NoArgsConstructor
//@AllArgsConstructor
@Getter
public class User {
    private int userId;
    private String userName;
    private String userPassword;
    private String userEmail;
    private String profileImage;
    private LocalDateTime createdAt;

    @Builder
    public User(String userName, String userPassword, String userEmail, String profileImage) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.profileImage = profileImage;
    }

    public void setPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
