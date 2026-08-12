package com.practice.practice_8_9.auth;


import com.practice.practice_8_9.user.UserService;
import com.practice.practice_8_9.user.dto.request.SignUpRequest;
import com.practice.practice_8_9.user.dto.response.SignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag( name = "Auth", description = "인증 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;




    //회원가입
    @Operation( summary = "회원가입", description = "이메일, 비밀번호 닉네임으로 회원가입합니다.")
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(
            @Valid @RequestBody SignUpRequest request
    ) {

        Long userId = userService.signup(request);

        SignUpResponse response = new SignUpResponse(userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
