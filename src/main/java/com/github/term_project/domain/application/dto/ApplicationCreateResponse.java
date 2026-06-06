package com.github.term_project.domain.application.dto;

public record ApplicationCreateResponse(
        Long applicationId,
        Long projectId,
        Integer applicationCount) {
}
