package com.github.term_project.global.auth;

import com.github.term_project.domain.user.entity.Role;
import com.github.term_project.domain.user.entity.User;
import java.io.Serializable;

public record SessionUser(Long id, String loginId, String nickname, Role role) implements Serializable {

    public static SessionUser from(User user) {
        return new SessionUser(user.getId(), user.getLoginId(), user.getNickname(), user.getRole());
    }
}