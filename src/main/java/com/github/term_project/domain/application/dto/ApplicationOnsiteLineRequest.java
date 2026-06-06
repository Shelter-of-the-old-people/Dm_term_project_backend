package com.github.term_project.domain.application.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record ApplicationOnsiteLineRequest(
        @JsonAlias("skillCategory") String position,
        String careerLevel,
        @JsonAlias("headCount") Integer headcount,
        @JsonAlias("monthlyPay") Integer monthlyWage,
        Integer sortOrder) {
}
