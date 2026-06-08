package com.github.term_project.domain.user.service;

import com.github.term_project.domain.user.dto.AuthRole;
import com.github.term_project.domain.user.dto.LoginRequest;
import com.github.term_project.domain.user.dto.SessionUserResponse;
import com.github.term_project.domain.user.dto.SignupRequest;
import com.github.term_project.domain.user.entity.User;
import com.github.term_project.domain.user.repository.UserRepository;
import com.github.term_project.global.auth.SessionConst;
import com.github.term_project.global.auth.SessionUser;
import com.github.term_project.global.error.BusinessException;
import com.github.term_project.global.error.ErrorCode;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public SessionUserResponse signup(SignupRequest request, HttpSession session) {
        String loginId = request.loginId().trim();

        if (userRepository.existsByLoginId(loginId)) {
            throw new BusinessException(ErrorCode.CONFLICT, "loginId already exists.");
        }

        User user = userRepository.save(User.builder()
                .loginId(loginId)
                .password(request.password().trim())
                .nickname(request.nickname().trim())
                .role(AuthRole.from(request.role()).toRole())
                .build());

        return storeSession(session, user);
    }

    @Transactional
    public SessionUserResponse login(LoginRequest request, HttpSession session) {
        User user = userRepository.findByLoginId(request.loginId().trim())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

        if (!user.getPassword().equals(request.password().trim())) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
        }

        return storeSession(session, user);
    }

    public SessionUserResponse getCurrentUser(SessionUser sessionUser) {
        return SessionUserResponse.from(sessionUser);
    }

    @Transactional
    public void logout(HttpSession session) {
        session.invalidate();
    }

    private SessionUserResponse storeSession(HttpSession session, User user) {
        SessionUser sessionUser = SessionUser.from(user);
        session.setAttribute(SessionConst.LOGIN_USER, sessionUser);
        return SessionUserResponse.from(sessionUser);
    }
}
