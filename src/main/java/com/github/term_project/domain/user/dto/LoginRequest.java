package com.github.term_project.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "loginId is required.") String loginId,
        @NotBlank(message = "password is required.") String password,
        @NotBlank(message = "role is required.") String role) {
}
