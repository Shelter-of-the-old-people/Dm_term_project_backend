package com.github.term_project.domain.user.controller;

import com.github.term_project.domain.user.dto.LoginRequest;
import com.github.term_project.domain.user.dto.SessionUserResponse;
import com.github.term_project.domain.user.dto.SignupRequest;
import com.github.term_project.domain.user.service.AuthService;
import com.github.term_project.global.auth.LoginUser;
import com.github.term_project.global.auth.SessionUser;
import com.github.term_project.global.common.ApiResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ApiResponse<SessionUserResponse> signup(
            @Valid @RequestBody SignupRequest request,
            HttpSession session) {
        return ApiResponse.ok(authService.signup(request, session));
    }

    @PostMapping("/login")
    public ApiResponse<SessionUserResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpSession session) {
        return ApiResponse.ok(authService.login(request, session));
    }

    @GetMapping("/session")
    public ApiResponse<SessionUserResponse> getSession(@LoginUser SessionUser sessionUser) {
        return ApiResponse.ok(authService.getCurrentUser(sessionUser));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpSession session) {
        authService.logout(session);
        return ApiResponse.ok();
    }
}
