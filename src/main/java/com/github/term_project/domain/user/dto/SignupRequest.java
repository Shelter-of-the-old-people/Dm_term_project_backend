package com.github.term_project.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank(message = "loginId is required.") String loginId,
        @NotBlank(message = "nickname is required.")
        @Size(max = 30, message = "nickname must be at most 30 characters.")
        String nickname,
        @NotBlank(message = "password is required.") String password,
        @NotBlank(message = "role is required.") String role) {
}
