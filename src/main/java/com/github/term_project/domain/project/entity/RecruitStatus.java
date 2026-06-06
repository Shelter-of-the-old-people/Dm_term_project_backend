package com.github.term_project.domain.project.entity;

public enum RecruitStatus {
    OPEN,
    URGENT,
    REVIEWING;

    public String toApiValue() {
        return switch (this) {
            case OPEN -> "open";
            case URGENT -> "urgent";
            case REVIEWING -> "reviewing";
        };
    }
}
