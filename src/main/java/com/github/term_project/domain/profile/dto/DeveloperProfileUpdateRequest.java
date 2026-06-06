package com.github.term_project.domain.profile.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record DeveloperProfileUpdateRequest(
        @NotNull List<String> supportFields,
        boolean activeAvailable,
        boolean onsiteAvailable,
        @NotBlank String regionSido,
        @NotBlank String regionSigungu,
        @NotBlank String businessType,
        @NotNull @Min(0) Integer careerYears,
        @NotNull List<String> searchTags,
        @NotBlank String introduction) {
}
