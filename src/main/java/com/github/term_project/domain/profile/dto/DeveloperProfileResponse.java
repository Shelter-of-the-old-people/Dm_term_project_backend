package com.github.term_project.domain.profile.dto;

import java.util.List;

public record DeveloperProfileResponse(
        Long userId,
        String loginId,
        String nickname,
        String profileImageUrl,
        List<String> supportFields,
        boolean activeAvailable,
        boolean onsiteAvailable,
        String regionSido,
        String regionSigungu,
        String businessType,
        int careerYears,
        List<String> searchTags,
        String introduction) {
}
