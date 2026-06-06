package com.github.term_project.domain.project.controller;

import com.github.term_project.domain.application.dto.ClientApplicantSummaryResponse;
import com.github.term_project.domain.project.dto.ClientProjectCreateRequest;
import com.github.term_project.domain.project.dto.ClientProjectCreateResponse;
import com.github.term_project.domain.project.dto.ClientProjectDetailResponse;
import com.github.term_project.domain.project.dto.ClientProjectSummaryResponse;
import com.github.term_project.domain.project.service.ClientProjectService;
import com.github.term_project.global.auth.LoginUser;
import com.github.term_project.global.auth.SessionUser;
import com.github.term_project.global.common.ApiResponse;
import com.github.term_project.global.common.PageResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client/projects")
@RequiredArgsConstructor
public class ClientProjectController {

    private final ClientProjectService clientProjectService;

    @PostMapping
    public ApiResponse<ClientProjectCreateResponse> createProject(
            @LoginUser SessionUser sessionUser,
            @Valid @RequestBody ClientProjectCreateRequest request) {
        return ApiResponse.ok(clientProjectService.createProject(sessionUser, request));
    }

    @GetMapping
    public ApiResponse<List<ClientProjectSummaryResponse>> getClientProjects(
            @LoginUser SessionUser sessionUser) {
        return ApiResponse.ok(clientProjectService.getClientProjects(sessionUser));
    }

    @GetMapping("/{projectId}")
    public ApiResponse<ClientProjectDetailResponse> getClientProject(
            @PathVariable Long projectId,
            @LoginUser SessionUser sessionUser) {
        return ApiResponse.ok(clientProjectService.getClientProject(projectId, sessionUser));
    }

    @GetMapping("/{projectId}/applicants")
    public ApiResponse<PageResponse<ClientApplicantSummaryResponse>> getClientProjectApplicants(
            @PathVariable Long projectId,
            @LoginUser SessionUser sessionUser,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "2") int size) {
        return ApiResponse.ok(clientProjectService.getProjectApplicants(projectId, sessionUser, page));
    }
}
