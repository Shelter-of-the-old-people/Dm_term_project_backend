package com.github.term_project.domain.application.controller;

import com.github.term_project.domain.application.dto.ClientApplicationDetailResponse;
import com.github.term_project.domain.project.service.ClientProjectService;
import com.github.term_project.global.auth.LoginUser;
import com.github.term_project.global.auth.SessionUser;
import com.github.term_project.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client/applications")
@RequiredArgsConstructor
public class ClientApplicationController {

    private final ClientProjectService clientProjectService;

    @GetMapping("/{applicationId}")
    public ApiResponse<ClientApplicationDetailResponse> getClientApplication(
            @PathVariable Long applicationId,
            @LoginUser SessionUser sessionUser) {
        return ApiResponse.ok(clientProjectService.getClientApplication(applicationId, sessionUser));
    }
}
