package com.bho.catchtrippingbackend.users.controller;

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

    /**
     * 사용자 등록
     * @param user 사용자 정보
     * @return 상태 메시지
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UsersDto user) {
        // 사용자 이름 중복 체크
        if (!usersService.isUsernameAvailable(user.getUserName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken.");
        }

        // 회원가입 시 비밀번호 형식 검사
        usersService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful.");
    }

    /**
     * 사용자 아이디 중복 확인
     * @param userName 사용자 아이디
     * @return 사용 가능 여부
     */
    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam("userName") String userName) {
        boolean isAvailable = usersService.isUsernameAvailable(userName);
        return ResponseEntity.ok(isAvailable);
    }

    /**
     * 이메일 중복 확인
     * @param userEmail 확인할 이메일 주소
     * @return 사용 가능 여부 (true: 사용 가능, false: 이미 사용 중)
     */
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailAvailability(@RequestParam("userEmail") String userEmail) {
        boolean isAvailable = usersService.isEmailAvailable(userEmail);
        return ResponseEntity.ok(isAvailable);
    }

    /**
     * 사용자 인증 상태 확인
     * 클라이언트 요청 시 현재 사용자가 인증된 상태인지 확인
     * @param authentication 현재 인증 객체
     * @return "Authenticated" 문자열 (인증된 상태인 경우)
     */
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkLogin(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }

    /**
     * 인증된 사용자 정보 조회
     * @param authentication 인증 객체
     * @return 사용자 정보
     */
    @GetMapping("/userinfo")
    public ResponseEntity<UserInfoDto> getUserInfo(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        UsersDto userDetails = usersService.findUserByUsername(user.getUsername());
        UserInfoDto userInfo = new UserInfoDto(
                user.getUsername(),
                userDetails.getUserEmail(),
                user.getAuthorities()
        );
        return ResponseEntity.ok(userInfo);
    }
}
