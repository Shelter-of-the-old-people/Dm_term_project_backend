package com.github.term_project.domain.project.dto;

import com.github.term_project.global.error.BusinessException;
import com.github.term_project.global.error.ErrorCode;
import java.util.Arrays;
import java.util.Locale;

public enum ProjectSortType {
    FREEMOA,
    LATEST,
    HIGH_BUDGET,
    LOW_BUDGET,
    DEADLINE;

    public static ProjectSortType from(String value) {
        if (value == null || value.isBlank()) {
            return FREEMOA;
        }

        return Arrays.stream(values())
                .filter(candidate -> candidate.name().equals(toEnumName(value)))
                .findFirst()
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.INVALID_INPUT,
                        "sort must be one of freemoa, latest, highBudget, lowBudget, deadline."));
    }

    private static String toEnumName(String value) {
        return switch (value.trim().toLowerCase(Locale.ROOT)) {
            case "freemoa" -> "FREEMOA";
            case "latest" -> "LATEST";
            case "highbudget" -> "HIGH_BUDGET";
            case "lowbudget" -> "LOW_BUDGET";
            case "deadline" -> "DEADLINE";
            default -> value.trim().toUpperCase(Locale.ROOT);
        };
    }
}
