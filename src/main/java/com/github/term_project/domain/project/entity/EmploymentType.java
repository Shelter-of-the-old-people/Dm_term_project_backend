package com.github.term_project.domain.project.entity;

import com.github.term_project.global.error.BusinessException;
import com.github.term_project.global.error.ErrorCode;
import java.util.Locale;

public enum EmploymentType {
    OUTSOURCING,
    RESIDENT;

    public String toApiValue() {
        return name().toLowerCase();
    }

    public static EmploymentType fromApiValue(String value) {
        if (value == null || value.isBlank()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, "employmentType is required.");
        }

        String normalized = value.trim().toLowerCase(Locale.ROOT);
        return switch (normalized) {
            case "outsourcing" -> OUTSOURCING;
            case "resident", "onsite" -> RESIDENT;
            default -> throw new BusinessException(
                    ErrorCode.INVALID_INPUT,
                    "employmentType must be one of outsourcing, resident, onsite.");
        };
    }
}
