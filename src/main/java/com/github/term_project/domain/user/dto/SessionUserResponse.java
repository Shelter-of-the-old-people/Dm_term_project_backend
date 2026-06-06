package com.github.term_project.domain.user.dto;

import com.github.term_project.domain.user.entity.Role;
import com.github.term_project.global.auth.SessionUser;

public record SessionUserResponse(
        Long id,
        String loginId,
        String nickname,
        String role) {

    public static SessionUserResponse from(SessionUser sessionUser) {
        return new SessionUserResponse(
                sessionUser.id(),
                sessionUser.loginId(),
                sessionUser.nickname(),
                toApiRole(sessionUser.role()));
    }

    private static String toApiRole(Role role) {
        return role.name().toLowerCase();
    }
}
