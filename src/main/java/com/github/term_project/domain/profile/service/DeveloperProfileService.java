package com.github.term_project.domain.profile.service;

import com.github.term_project.domain.profile.dto.DeveloperProfileImageUploadResponse;
import com.github.term_project.domain.profile.dto.DeveloperProfileResponse;
import com.github.term_project.domain.profile.dto.DeveloperProfileUpdateRequest;
import com.github.term_project.domain.profile.entity.DeveloperProfile;
import com.github.term_project.domain.profile.repository.DeveloperProfileRepository;
import com.github.term_project.domain.user.entity.Role;
import com.github.term_project.domain.user.entity.User;
import com.github.term_project.domain.user.repository.UserRepository;
import com.github.term_project.global.auth.SessionUser;
import com.github.term_project.global.error.BusinessException;
import com.github.term_project.global.error.ErrorCode;
import com.github.term_project.global.infra.FileStorageService;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeveloperProfileService {

    private static final int MAX_SEARCH_TAGS = 5;

    private final DeveloperProfileRepository developerProfileRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public DeveloperProfileResponse getProfile(SessionUser sessionUser) {
        User developer = getDeveloper(sessionUser);
        return toResponse(getOrCreateProfile(developer));
    }

    @Transactional
    public DeveloperProfileResponse updateProfile(
            SessionUser sessionUser,
            DeveloperProfileUpdateRequest request) {
        User developer = getDeveloper(sessionUser);
        DeveloperProfile profile = getOrCreateProfile(developer);

        List<String> supportFields = normalizeDistinctValues(request.supportFields(), "supportFields");
        if (supportFields.isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, "supportFields must contain at least one item.");
        }

        List<String> searchTags = normalizeDistinctValues(request.searchTags(), "searchTags");
        if (searchTags.size() > MAX_SEARCH_TAGS) {
            throw new BusinessException(ErrorCode.TAG_LIMIT_EXCEEDED);
        }

        profile.updateProfile(
                supportFields,
                request.activeAvailable(),
                request.onsiteAvailable(),
                request.regionSido().trim(),
                request.regionSigungu().trim(),
                request.businessType().trim(),
                request.careerYears(),
                searchTags,
                request.introduction().trim());

        return toResponse(profile);
    }

    @Transactional
    public DeveloperProfileImageUploadResponse uploadProfileImage(
            SessionUser sessionUser,
            MultipartFile file) {
        User developer = getDeveloper(sessionUser);
        DeveloperProfile profile = getOrCreateProfile(developer);

        String imageUrl = fileStorageService.storeProfileImage(file);
        profile.updateProfileImagePath(imageUrl);

        return new DeveloperProfileImageUploadResponse(imageUrl);
    }

    @Transactional
    public DeveloperProfile ensureSeedProfile(User developer, DeveloperProfileUpdateRequest request, String imageUrl) {
        DeveloperProfile profile = getOrCreateProfile(developer);
        profile.updateProfile(
                normalizeDistinctValues(request.supportFields(), "supportFields"),
                request.activeAvailable(),
                request.onsiteAvailable(),
                request.regionSido().trim(),
                request.regionSigungu().trim(),
                request.businessType().trim(),
                request.careerYears(),
                normalizeDistinctValues(request.searchTags(), "searchTags"),
                request.introduction().trim());
        profile.updateProfileImagePath(imageUrl);
        return profile;
    }

    private DeveloperProfile getOrCreateProfile(User developer) {
        return developerProfileRepository.findByUser_Id(developer.getId())
                .orElseGet(() -> developerProfileRepository.save(DeveloperProfile.createDefault(developer)));
    }

    private User getDeveloper(SessionUser sessionUser) {
        User user = userRepository.findById(sessionUser.id())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (user.getRole() != Role.DEVELOPER) {
            throw new BusinessException(ErrorCode.ROLE_MISMATCH, "Developer access only.");
        }
        return user;
    }

    private DeveloperProfileResponse toResponse(DeveloperProfile profile) {
        User user = profile.getUser();
        return new DeveloperProfileResponse(
                user.getId(),
                user.getLoginId(),
                user.getNickname(),
                profile.getProfileImagePath(),
                List.copyOf(profile.getSupportFields()),
                profile.isActiveAvailable(),
                profile.isOnsiteAvailable(),
                profile.getRegionSido(),
                profile.getRegionSigungu(),
                profile.getBusinessType(),
                profile.getCareerYears(),
                List.copyOf(profile.getSearchTags()),
                profile.getIntroduction());
    }

    private List<String> normalizeDistinctValues(List<String> values, String fieldName) {
        if (values == null) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, fieldName + " is required.");
        }

        Set<String> deduplicated = new LinkedHashSet<>();
        for (String value : values) {
            if (value == null || value.isBlank()) {
                continue;
            }
            deduplicated.add(value.trim());
        }
        return new ArrayList<>(deduplicated);
    }
}
