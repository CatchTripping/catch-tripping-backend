package com.bho.catchtrippingbackend.users.controller;

import com.bho.catchtrippingbackend.security.CustomUserDetailsService;
import com.bho.catchtrippingbackend.users.model.UserInfoDto;
import com.bho.catchtrippingbackend.users.model.UsersDto;
import com.bho.catchtrippingbackend.users.model.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UsersDto user) {
        usersService.registerUser(user);
    }

    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public String checkAuthentication(Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("Unauthenticated");
        }
        return "Authenticated";
    }

    @GetMapping("/userinfo")
    public ResponseEntity<UserInfoDto> getUserInfo(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        UserInfoDto userInfo = new UserInfoDto(user.getUsername(), user.getAuthorities());
        return ResponseEntity.ok(userInfo);
    }
}
