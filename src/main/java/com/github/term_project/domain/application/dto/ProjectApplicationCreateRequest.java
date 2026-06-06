package com.github.term_project.domain.application.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record ProjectApplicationCreateRequest(
        @JsonAlias("applicationType") @NotBlank String employmentType,
        Integer workDays,
        Integer bidAmount,
        @NotBlank String content,
        @Valid List<ApplicationOnsiteLineRequest> onsiteLines,
        String position,
        String careerLevel,
        Integer headcount,
        Integer monthlyWage) {
}
