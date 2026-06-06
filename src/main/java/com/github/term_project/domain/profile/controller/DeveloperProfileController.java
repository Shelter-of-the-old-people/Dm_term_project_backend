package com.github.term_project.domain.profile.controller;

import com.github.term_project.domain.profile.dto.DeveloperProfileImageUploadResponse;
import com.github.term_project.domain.profile.dto.DeveloperProfileResponse;
import com.github.term_project.domain.profile.dto.DeveloperProfileUpdateRequest;
import com.github.term_project.domain.profile.service.DeveloperProfileService;
import com.github.term_project.global.auth.LoginUser;
import com.github.term_project.global.auth.SessionUser;
import com.github.term_project.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/developer/profile")
@RequiredArgsConstructor
public class DeveloperProfileController {

    private final DeveloperProfileService developerProfileService;

    @GetMapping
    public ApiResponse<DeveloperProfileResponse> getProfile(@LoginUser SessionUser sessionUser) {
        return ApiResponse.ok(developerProfileService.getProfile(sessionUser));
    }

    @PutMapping
    public ApiResponse<DeveloperProfileResponse> updateProfile(
            @LoginUser SessionUser sessionUser,
            @Valid @RequestBody DeveloperProfileUpdateRequest request) {
        return ApiResponse.ok(developerProfileService.updateProfile(sessionUser, request));
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<DeveloperProfileImageUploadResponse> uploadProfileImage(
            @LoginUser SessionUser sessionUser,
            @RequestPart("file") MultipartFile file) {
        return ApiResponse.ok(developerProfileService.uploadProfileImage(sessionUser, file));
    }
}
