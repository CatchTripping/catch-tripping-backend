package com.bho.catchtrippingbackend.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private String userName;
    private Collection<?> authorities;
}
