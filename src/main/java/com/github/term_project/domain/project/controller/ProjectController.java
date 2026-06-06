package com.github.term_project.domain.project.controller;

import com.github.term_project.domain.project.dto.ProjectDetailResponse;
import com.github.term_project.domain.project.dto.ProjectListItemResponse;
import com.github.term_project.domain.project.service.ProjectService;
import com.github.term_project.global.common.ApiResponse;
import com.github.term_project.global.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ApiResponse<PageResponse<ProjectListItemResponse>> getProjects(
            @RequestParam(defaultValue = "all") String type,
            @RequestParam(defaultValue = "freemoa") String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "4") int size) {
        return ApiResponse.ok(projectService.getProjects(type, sort, page, size));
    }

    @GetMapping("/{projectId}")
    public ApiResponse<ProjectDetailResponse> getProject(@PathVariable Long projectId) {
        return ApiResponse.ok(projectService.getProject(projectId));
    }
}
