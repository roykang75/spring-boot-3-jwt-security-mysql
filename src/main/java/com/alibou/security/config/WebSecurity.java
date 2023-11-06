package com.alibou.security.config;

import com.alibou.security.advice.exception.ForbiddenException;
import com.alibou.security.advice.exception.UserNotFoundException;
import com.alibou.security.policy.PolicyService;
import com.alibou.security.user.User;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSecurity {

    private final PolicyService policyService;

    public boolean check(Authentication authentication, HttpServletRequest request)
            throws AccessDeniedException {
        log.debug("## authentication: {}", authentication.toString());
        log.debug("## requestURI : {}", request.getRequestURI());

        User user = null;
        try {
            user = (User) authentication.getPrincipal();
            log.debug("## user: {}", user.toString());
        } catch (Exception e) {
            return false;
        }
        return policyService.hasPermission(user.getRole().getPolicies(), request.getRequestURI());
    }
}
