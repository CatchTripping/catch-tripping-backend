package com.bho.catchtrippingbackend.user.controller;

import com.amazonaws.HttpMethod;
import com.bho.catchtrippingbackend.error.SystemException;
import com.bho.catchtrippingbackend.error.code.ClientErrorCode;
import com.bho.catchtrippingbackend.error.response.CustomResponse;
import com.bho.catchtrippingbackend.s3.service.S3Service;
import com.bho.catchtrippingbackend.security.CustomUserDetails;
import com.bho.catchtrippingbackend.user.dto.*;
import com.bho.catchtrippingbackend.user.entity.User;
import com.bho.catchtrippingbackend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final S3Service s3Service;

    /**
     * 사용자 등록
     * 주어진 사용자 정보를 사용하여 새로운 사용자를 등록합니다.
     * @param request 사용자 정보 (userName, userPassword, userEmail)
     * @return 등록된 사용자 ID를 포함한 응답 DTO
     */
    @PostMapping("/register")
    public ResponseEntity<CustomResponse<UserRegisterResponseDto>> registerUser(@Valid @RequestBody UserRegisterRequestDto request) {
        UserRegisterResponseDto response = userService.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(CustomResponse.success(response));
    }

    /**
     * 사용자 아이디 중복 확인
     * 주어진 사용자 아이디가 사용 가능한지 확인합니다.
     * @param userName 사용자 아이디 (username)
     * @return 사용 가능 여부 (true: 사용 가능, false: 중복)
     */
    @GetMapping("/check-username")
    public ResponseEntity<CustomResponse<CheckUsernameResponseDto>> checkUsername(@RequestParam("userName") String userName) {
        boolean isAvailable = userService.isUsernameAvailable(userName);
        // 상태 메시지와 데이터를 포함한 응답 반환
        CheckUsernameResponseDto response = new CheckUsernameResponseDto(isAvailable);
        String message = isAvailable ? "사용 가능한 아이디입니다." : "이미 사용 중인 아이디입니다.";

        return ResponseEntity.ok(CustomResponse.success(response, message));
    }

    /**
     * 이메일 중복 확인
     * 주어진 이메일이 사용 가능한지 확인합니다.
     * @param userEmail 확인할 이메일 주소
     * @return 사용 가능 여부 (true: 사용 가능, false: 이미 사용 중)
     */
    @GetMapping("/check-email")
    public ResponseEntity<CustomResponse<CheckEmailResponseDto>> checkEmailAvailability(@RequestParam("userEmail") String userEmail) {
        boolean isAvailable = userService.isEmailAvailable(userEmail);
        CheckEmailResponseDto response = new CheckEmailResponseDto(isAvailable);
        return ResponseEntity.ok(CustomResponse.success(response));
    }

    /**
     * 사용자 인증 상태 확인
     * 클라이언트가 요청한 사용자 인증 상태를 확인하여 인증 여부를 반환합니다.
     * @param authentication 현재 인증 객체
     * @return 인증 상태 (true: 인증됨, false: 인증되지 않음)
     */
    @GetMapping("/check")
    public ResponseEntity<CheckLoginResponseDto> checkLogin(Authentication authentication) {
//        // 인증되지 않은 사용자 처리
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new SystemException(ClientErrorCode.NOT_AUTHORIZED);
//        }
//
//        // 인증된 사용자: 성공 응답
//        CheckLoginResponseDto response = new CheckLoginResponseDto(true);
//        return ResponseEntity.ok(CustomResponse.success(response));
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        CheckLoginResponseDto response = new CheckLoginResponseDto(isAuthenticated);

        return ResponseEntity.ok(response);
    }

    /**
     * 인증된 사용자 정보 조회
     * 현재 인증된 사용자의 정보를 조회하여 반환합니다.
     * @param authentication 인증 객체
     * @return 인증된 사용자 정보 (userName, userEmail, profileImage, 권한 목록)
     */
    @GetMapping("/userinfo")
    public ResponseEntity<CustomResponse<UserDto>> getUserInfo(Authentication authentication) {

//        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.findUserByUsername(userDetails.getUsername());

        if (user == null) {
            // 유효한 사용자 정보가 없다면 404
            throw new SystemException(ClientErrorCode.USER_NOT_FOUND);
        }

        UserDto userInfo = UserDto.fromEntity(
                user,
                s3Service.generatePresignedUrl(user.getProfileImage(), HttpMethod.GET),
                userDetails.getAuthorities()
        );
        return ResponseEntity.ok(CustomResponse.success(userInfo));
    }

    @PostMapping("/profile-image")
    public ResponseEntity<String> uploadProfileImage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody Map<String, String> request) {
        String tempImageKey = request.get("imageKey");
        userService.uploadProfileImage(userDetails.getUserId(), tempImageKey);
        return ResponseEntity.ok("프로필 이미지 업로드 완료");
    }

    @DeleteMapping("/profile-image")
    public ResponseEntity<String> deleteProfileImage(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.deleteProfileImage(userDetails.getUserId());
        return ResponseEntity.ok("프로필 이미지 삭제 완료");
    }

    @GetMapping("/profile-image")
    public ResponseEntity<Map<String, String>> getProfileImage(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        String imageUrl = userService.getProfileImageUrl(userDetails.getUserId());
        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", imageUrl);
        return ResponseEntity.ok(response);
    }
}