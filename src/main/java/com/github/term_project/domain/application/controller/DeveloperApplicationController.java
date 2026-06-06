package com.github.term_project.domain.application.controller;

import com.github.term_project.domain.application.dto.DeveloperApplicationDetailResponse;
import com.github.term_project.domain.application.dto.DeveloperApplicationSummaryResponse;
import com.github.term_project.domain.application.service.ApplicationService;
import com.github.term_project.global.auth.LoginUser;
import com.github.term_project.global.auth.SessionUser;
import com.github.term_project.global.common.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/developer/applications")
@RequiredArgsConstructor
public class DeveloperApplicationController {

    private final ApplicationService applicationService;

    @GetMapping
    public ApiResponse<List<DeveloperApplicationSummaryResponse>> getMyApplications(
            @LoginUser SessionUser sessionUser) {
        return ApiResponse.ok(applicationService.getDeveloperApplications(sessionUser));
    }

    @GetMapping("/{applicationId}")
    public ApiResponse<DeveloperApplicationDetailResponse> getMyApplication(
            @PathVariable Long applicationId,
            @LoginUser SessionUser sessionUser) {
        return ApiResponse.ok(applicationService.getDeveloperApplication(applicationId, sessionUser));
    }
}
