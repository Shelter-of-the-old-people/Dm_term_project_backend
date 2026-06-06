package com.github.term_project.domain.user.dto;

import com.github.term_project.domain.user.entity.Role;
import com.github.term_project.global.error.BusinessException;
import com.github.term_project.global.error.ErrorCode;
import java.util.Arrays;
import java.util.Locale;

public enum AuthRole {
    DEVELOPER,
    CLIENT;

    public Role toRole() {
        return Role.valueOf(name());
    }

    public static AuthRole from(String value) {
        if (value == null || value.isBlank()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, "role is required.");
        }

        return Arrays.stream(values())
                .filter(candidate -> candidate.name().equals(value.trim().toUpperCase(Locale.ROOT)))
                .findFirst()
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.INVALID_INPUT,
                        "role must be one of developer, client."));
    }
}
