package com.bho.catchtrippingbackend.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    private int userId;
    private String userName;
    private String userPassword;
    private String userEmail;
}
