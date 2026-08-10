package com.practice.practice_8_9.auth;


import com.practice.practice_8_9.user.UserService;
import com.practice.practice_8_9.user.dto.request.SignUpRequest;
import com.practice.practice_8_9.user.dto.response.SignUpResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;




    //회원가입
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
